package com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Payload to response a new expenses")
public record NewExpenseRequest(
        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Date cannot be null")
        @PastOrPresent(message = "Date cannot be in the future")
        LocalDate date,

        @NotNull(message = "Category ID cannot be null")
        UUID categoryId
) {
}
