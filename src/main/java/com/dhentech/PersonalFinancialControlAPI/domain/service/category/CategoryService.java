package com.dhentech.PersonalFinancialControlAPI.domain.service.category;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createNewCategory(NewCategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(UUID id, NewCategoryRequest request);
}
