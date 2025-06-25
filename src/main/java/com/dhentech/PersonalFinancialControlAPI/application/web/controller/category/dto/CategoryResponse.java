package com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}
