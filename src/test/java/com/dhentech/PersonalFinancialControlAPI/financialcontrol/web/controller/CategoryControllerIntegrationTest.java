package com.dhentech.PersonalFinancialControlAPI.financialcontrol.web.controller;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/sql/reset-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID testCategoryId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

    @Test
    @DisplayName("Given valid data, when creating a new category, then return 201 Created")
    void whenPostNewCategory_withValidData_thenReturns201Created() throws Exception {
        // --- GIVEN (Arrange) ---
        // We use a unique name to avoid conflicts with existing data or other tests.
        String categoryName = "Supermarket";
        var request = new NewCategoryRequest(categoryName);

        // --- WHEN (Act) & THEN (Assert) ---
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // 1. Assert that the HTTP status is 201 Created
                .andExpect(status().isCreated())
                // 2. Assert that the response body contains the expected data
                .andExpect(jsonPath("$.id").exists()) // Check that an ID was generated
                .andExpect(jsonPath("$.name").value(categoryName));
    }

    @Test
    @DisplayName("Given invalid data (blank name), when creating a new category, then return 400 Bad Request")
    void whenPostNewCategory_withInvalidName_thenReturns400BadRequest() throws Exception {
        // --- GIVEN (Arrange) ---
        var invalidRequest = new NewCategoryRequest(""); // Invalid data

        // --- WHEN (Act) & THEN (Assert) ---
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                // 1. Assert that the HTTP status is 400 Bad Request
                .andExpect(status().isBadRequest())
                // 2. Assert the structure of the standardized error response
                .andExpect(jsonPath("$.title").value("Invalid Data"))
                .andExpect(jsonPath("$.status").value(400))
                // 3. Assert that the 'fields' array for the 'name' field contains all expected error messages, in any order.
                .andExpect(jsonPath("$.fields[?(@.name == 'name')].userMessage",
                        containsInAnyOrder("Name field cannot be empty", "Property Name field Category must be between 1 and 50 characters")));
    }

    @Test
    @DisplayName("When getting all categories, then return 200 OK with a list of categories")
    void whenGetAllCategories_thenReturns200OkAndListOfCategories() throws Exception {
        // --- GIVEN (Arrange) ---
        // The @Sql script ensures the database is in a known state.

        // --- WHEN (Act) & THEN (Assert) ---
        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                // 1. Assert that the HTTP status is 200 OK
                .andExpect(status().isOk())
                // 2. Assert that the response is a JSON array
                .andExpect(jsonPath("$").isArray())
                // 3. Assert that the array contains exactly one element (from the reset script)
                .andExpect(jsonPath("$", hasSize(1)))
                // 4. Assert the details of the first category in the list (our test category)
                .andExpect(jsonPath("$[0].id").value(testCategoryId.toString()))
                .andExpect(jsonPath("$[0].name").value("Test Category"));
    }
}
