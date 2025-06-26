package com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Payload to response a new expenses")
public record NewExpenseRequest(
        String description,
        BigDecimal amount,
        LocalDate date,
        UUID categoryId
) {
}
