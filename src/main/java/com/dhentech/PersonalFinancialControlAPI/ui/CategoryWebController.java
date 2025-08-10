package com.dhentech.PersonalFinancialControlAPI.ui;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.CategoryResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.category.dto.NewCategoryRequest;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.BusinessRuleException;
import com.dhentech.PersonalFinancialControlAPI.domain.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/categories") // Base path for all web-related category actions
public class CategoryWebController {

    private final CategoryService categoryService;

    public CategoryWebController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Displays the page with a list of all categories.
     * Corresponds to the old GET /api/categories endpoint.
     */
    @GetMapping
    public String listCategories(Model model) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list"; // Renders templates/categories/list.html
    }

    /**
     * Displays the form to create a new category.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryRequest", new NewCategoryRequest(""));
        return "categories/form"; // Renders templates/categories/form.html
    }

    /**
     * Processes the submission of the new category form.
     * Corresponds to the old POST /api/categories endpoint.
     */
    @PostMapping
    public String createCategory(@Valid @ModelAttribute("categoryRequest") NewCategoryRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "categories/form"; // If there are validation errors, show the form again
        }
        try {
            categoryService.createNewCategory(request);
            return "redirect:/categories"; // Redirect to the list page after successful creation
        } catch (BusinessRuleException ex) {
            model.addAttribute("businessError", ex.getMessage());
            return "categories/form";
        }
    }

    /**
     * Displays the form to edit an existing category.
     */
    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable UUID id, Model model) {
        // We need to fetch the category to pre-populate the form
        // Note: It's better to have a findById method in the service that returns the DTO
        CategoryResponse category = categoryService.findCategoryById(id); // Assuming this method exists
        model.addAttribute("categoryId", id);
        model.addAttribute("categoryRequest", new NewCategoryRequest(category.name()));
        return "categories/form"; // Reuse the same form for editing
    }

    /**
     * Processes the submission of the category update form.
     * Corresponds to the old PUT /api/categories/{id} endpoint.
     */
    @PostMapping("/{id}")
    public String updateCategory(@PathVariable UUID id, @Valid @ModelAttribute("categoryRequest") NewCategoryRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryId", id);
            return "categories/form"; // Show form again on error
        }
        try {
            categoryService.updateCategoryByWeb(id, request);
            return "redirect:/categories"; // Redirect to the list page
        } catch (BusinessRuleException ex) {
            // If a business rule is violated (e.g., name already exists),
            // add the error message to the model and show the form again.
            model.addAttribute("categoryId", id);
            model.addAttribute("businessError", ex.getMessage());
            return "categories/form";
        }
    }
}
