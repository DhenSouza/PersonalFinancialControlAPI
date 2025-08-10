package com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Payload to response a Categories")
public record CategoryResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
