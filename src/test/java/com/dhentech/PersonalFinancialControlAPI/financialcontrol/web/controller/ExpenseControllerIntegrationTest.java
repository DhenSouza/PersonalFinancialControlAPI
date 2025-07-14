package com.dhentech.PersonalFinancialControlAPI.financialcontrol.web.controller;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.NewExpenseRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID testCategoryId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");


    @Test
    void whenPostNewExpense_withValidData_thenReturns201Created() throws Exception {
        // --- GIVEN (Arrange) ---
        var request = new NewExpenseRequest(
                "New test expense",
                new BigDecimal("123.45"),
                LocalDate.now(),
                testCategoryId
        );

        // --- WHEN (Act) & THEN (Assert) ---
        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("New test expense"))
                .andExpect(jsonPath("$.amount").value(123.45))
                .andExpect(jsonPath("$.category.id").value(testCategoryId.toString()));
    }

    @Test
    @DisplayName("Given invalid data (blank description), when creating a new expense, then return 400 Bad Request")
    void whenPostNewExpense_withInvalidDescription_thenReturns400BadRequest() throws Exception {
        // Arrange
        var invalidRequest = new NewExpenseRequest(
                "",
                new BigDecimal("99.99"),
                LocalDate.now(),
                testCategoryId
        );

        // ACT and Asserts
        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                // 1. Assert that the HTTP status is 400 Bad Request
                .andExpect(status().isBadRequest())
                // 2. Assert the structure of your standardized error response
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("/invalid-data"))
                .andExpect(jsonPath("$.title").value("Invalid Data"))
                .andExpect(jsonPath("$.instance").value("/api/expenses"))
                .andExpect(jsonPath("$.userMessage").value("One or more fields are invalid. Please check and try again."))
                // 3. Assert the specific field error
                .andExpect(jsonPath("$.fields[0].name").value("description"))
                .andExpect(jsonPath("$.fields[0].userMessage").value("Description cannot be blank"));
    }

    @Test
    @DisplayName("Given a non-existent category ID, when creating a new expense, then return 404 Not Found")
    void whenPostNewExpense_withNonExistentCategory_thenReturns404NotFound() throws Exception {
        // --- GIVEN (Arrange) ---
        UUID nonExistentCategoryId = UUID.randomUUID();
        var requestWithInvalidCategory = new NewExpenseRequest(
                "Expense with invalid category",
                new BigDecimal("50.00"),
                LocalDate.now(),
                nonExistentCategoryId
        );

        // --- WHEN (Act) & THEN (Assert) ---
        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithInvalidCategory)))
                // 1. Assert that the HTTP status is 404 Not Found
                .andExpect(status().isNotFound())
                // 2. Assert the structure of the standardized error response for a 404
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Resource Not Found"))
                .andExpect(jsonPath("$.userMessage").value("The resource you tried to access does not exist."))
                // 3. Assert the specific detail message contains the non-existent ID
                .andExpect(jsonPath("$.detail").value("Category not found with id: " + nonExistentCategoryId));
    }
}
