package com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload to create a new Category")
public record NewCategoryRequest(
        @Size(min = 1, max = 50, message = "Property Name field Category must be between 1 and 50 characters")
        @NotEmpty(message = "Name field cannot be empty")
        String name
) {
}
