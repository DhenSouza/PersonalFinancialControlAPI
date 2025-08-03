package com.dhentech.PersonalFinancialControlAPI.financialcontrol.domain.service;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;
import com.dhentech.PersonalFinancialControlAPI.application.web.mapper.CategoryMapper;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.BusinessRuleException;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.ResourceNotFoundException;
import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import com.dhentech.PersonalFinancialControlAPI.domain.service.category.CategoryServiceImpl;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Given valid data, when creating a new category, then it should succeed")
    void createNewCategory_withValidData_shouldSucceed() {
        // --- GIVEN (Arrange) ---
        var request = new NewCategoryRequest("Food");
        var category = new Category(request.name());
        var response = new CategoryResponse(UUID.randomUUID(), request.name());

        when(categoryRepository.existsByName(request.name())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(response);

        // --- WHEN (Act) ---
        CategoryResponse actualResponse = categoryService.createNewCategory(request);

        // --- THEN (Assert) ---
        assertNotNull(actualResponse);
        assertEquals(response.name(), actualResponse.name());
        verify(categoryRepository, times(1)).existsByName(request.name());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Given an existing category name, when creating a new category, then it should throw BusinessRuleException")
    void createNewCategory_withExistingName_shouldThrowBusinessRuleException() {
        // --- GIVEN (Arrange) ---
        var request = new NewCategoryRequest("Food");
        when(categoryRepository.existsByName(request.name())).thenReturn(true);

        // --- WHEN (Act) & THEN (Assert) ---
        assertThrows(BusinessRuleException.class, () -> {
            categoryService.createNewCategory(request);
        });

        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("When getting all categories, then it should return a list of category responses")
    void getAllCategories_shouldReturnListOfCategoryResponses() {
        // --- GIVEN (Arrange) ---
        var category = new Category("Food");
        var response = new CategoryResponse(UUID.randomUUID(), "Food");

        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(response);

        // --- WHEN (Act) ---
        List<CategoryResponse> actualList = categoryService.getAllCategories();

        // --- THEN (Assert) ---
        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        assertEquals(response.name(), actualList.get(0).name());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given a valid ID and data, when updating a category, then it should succeed")
    void updateCategory_withValidId_shouldSucceed() {
        // --- GIVEN (Arrange) ---
        UUID categoryId = UUID.randomUUID();
        var request = new NewCategoryRequest("Groceries");
        var existingCategory = new Category("Food");
        var response = new CategoryResponse(categoryId, request.name());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(response);

        // --- WHEN (Act) ---
        CategoryResponse actualResponse = categoryService.updateCategory(categoryId, request);

        // --- THEN (Assert) ---
        assertNotNull(actualResponse);
        assertEquals(request.name(), actualResponse.name());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Given a non-existent ID, when updating a category, then it should throw ResourceNotFoundException")
    void updateCategory_withNonExistentId_shouldThrowResourceNotFoundException() {
        // --- GIVEN (Arrange) ---
        UUID nonExistentId = UUID.randomUUID();
        var request = new NewCategoryRequest("Groceries");
        when(categoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // --- WHEN (Act) & THEN (Assert) ---
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(nonExistentId, request);
        });

        verify(categoryRepository, never()).save(any(Category.class));
    }
}
