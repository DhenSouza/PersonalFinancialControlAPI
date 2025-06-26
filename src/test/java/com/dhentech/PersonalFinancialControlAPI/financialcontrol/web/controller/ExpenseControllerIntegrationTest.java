package com.dhentech.PersonalFinancialControlAPI.financialcontrol.web.controller;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.NewExpenseRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
