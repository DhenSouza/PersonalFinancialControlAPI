package com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Payload to create a new Expense")
public record ExpenseResponse(
        UUID id,
        String description,
        BigDecimal amount,
        LocalDate date,
        CategoryResponse category
) {
}
