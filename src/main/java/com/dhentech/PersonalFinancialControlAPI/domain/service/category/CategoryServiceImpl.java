package com.dhentech.PersonalFinancialControlAPI.domain.service.category;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;
import com.dhentech.PersonalFinancialControlAPI.application.web.mapper.CategoryMapper;
import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    @Transactional
    public CategoryResponse createNewCategory(NewCategoryRequest request) {

        Category newCategory = new Category(request.name());

        Category savedCategory = this.categoryRepository.save(newCategory);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    @Transactional
    public List<CategoryResponse> getAllCategories() {
        return this.categoryRepository.findAll().stream().map(categoryMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID id, NewCategoryRequest request) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryToUpdate.updateName(request.name());

        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        return categoryMapper.toResponse(updatedCategory);
    }
}
