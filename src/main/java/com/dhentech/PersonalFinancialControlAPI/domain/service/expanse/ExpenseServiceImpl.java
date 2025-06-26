package com.dhentech.PersonalFinancialControlAPI.domain.service.expanse;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.ExpenseResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.NewExpenseRequest;
import com.dhentech.PersonalFinancialControlAPI.application.web.mapper.ExpenseMapper;
import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import com.dhentech.PersonalFinancialControlAPI.domain.model.expense.Expense;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.CategoryRepository;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    @Transactional
    public ExpenseResponse createExpanse(NewExpenseRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.categoryId()));

        Expense expense = new Expense(
                request.description(),
                request.amount(),
                request.date(),
                category
        );

        Expense savedExpense = expenseRepository.save(expense);

        return expenseMapper.toResponse(savedExpense);
    }
}
