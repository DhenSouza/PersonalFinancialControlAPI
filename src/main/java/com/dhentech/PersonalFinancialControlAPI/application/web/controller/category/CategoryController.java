package com.dhentech.PersonalFinancialControlAPI.application.web.controller.category;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;
import com.dhentech.PersonalFinancialControlAPI.domain.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createNewCategory(@Valid @RequestBody NewCategoryRequest request) {
        CategoryResponse createdCategory = this.categoryService.createNewCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id, @Valid @RequestBody NewCategoryRequest request) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updatedCategory);
    }
}
