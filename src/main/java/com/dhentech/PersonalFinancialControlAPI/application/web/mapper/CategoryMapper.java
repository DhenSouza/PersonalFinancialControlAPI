package com.dhentech.PersonalFinancialControlAPI.application.web.mapper;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }
}
