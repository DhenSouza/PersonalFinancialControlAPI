package com.dhentech.PersonalFinancialControlAPI.domain.service.category;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;
import com.dhentech.PersonalFinancialControlAPI.application.web.mapper.CategoryMapper;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.BusinessRuleException;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.ResourceNotFoundException;
import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

        if (categoryRepository.existsByName(request.name())) {
            throw new BusinessRuleException("A category with the name '" + request.name() + "' already exists.");
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        categoryToUpdate.updateName(request.name());

        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategoryByWeb(UUID id, NewCategoryRequest request) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        Optional<Category> existingCategoryWithName = categoryRepository.findByName(request.name());

        if (existingCategoryWithName.isPresent() && !existingCategoryWithName.get().getId().equals(id)) {
            throw new BusinessRuleException("A category with the name '" + request.name() + "' already exists.");
        }

        categoryToUpdate.updateName(request.name());
        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
