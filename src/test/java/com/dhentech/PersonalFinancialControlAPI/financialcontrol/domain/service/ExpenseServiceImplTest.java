package com.dhentech.PersonalFinancialControlAPI.financialcontrol.domain.service;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.ExpenseResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.NewExpenseRequest;
import com.dhentech.PersonalFinancialControlAPI.application.web.mapper.ExpenseMapper;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.ResourceNotFoundException;
import com.dhentech.PersonalFinancialControlAPI.domain.model.category.Category;
import com.dhentech.PersonalFinancialControlAPI.domain.model.expense.Expense;
import com.dhentech.PersonalFinancialControlAPI.domain.service.expanse.ExpenseServiceImpl;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.CategoryRepository;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseServiceImpl expenseService;


    private NewExpenseRequest newExpenseRequest;
    private Category category;
    private Expense expense;
    private ExpenseResponse expenseResponse;

    @BeforeEach
    void setUp() {
        UUID categoryId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();

        newExpenseRequest = new NewExpenseRequest(
                "Lunch",
                new BigDecimal("50.00"),
                LocalDate.now(),
                categoryId
        );

        category = new Category("Food");

        expense = new Expense(
                newExpenseRequest.description(),
                newExpenseRequest.amount(),
                newExpenseRequest.date(),
                category
        );

        expenseResponse = new ExpenseResponse(expenseId, "Lunch", new BigDecimal("50.00"), LocalDate.now(), null);
    }

    @Test
    @DisplayName("Given a valid request, when creating an expense, then it should return a valid ExpenseResponse")
    void createExpanse_withValidData_shouldReturnExpenseResponse() {
        // --- GIVEN (Arrange) ---
        when(categoryRepository.findById(newExpenseRequest.categoryId())).thenReturn(Optional.of(category));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        when(expenseMapper.toResponse(any(Expense.class))).thenReturn(expenseResponse);

        // --- WHEN (Act) ---
        ExpenseResponse actualResponse = expenseService.createExpanse(newExpenseRequest);

        // --- THEN (Assert) ---
        assertNotNull(actualResponse);
        assertEquals(expenseResponse.description(), actualResponse.description());

        verify(categoryRepository, times(1)).findById(newExpenseRequest.categoryId());
        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(expenseMapper, times(1)).toResponse(any(Expense.class));
    }

    @Test
    @DisplayName("Given a non-existent category ID, when creating an expense, then it should throw ResourceNotFoundException")
    void createExpanse_whenCategoryNotFound_shouldThrowResourceNotFoundException() {
        // --- GIVEN (Arrange) ---
        when(categoryRepository.findById(newExpenseRequest.categoryId())).thenReturn(Optional.empty());

        // --- WHEN (Act) & THEN (Assert) ---
        assertThrows(ResourceNotFoundException.class, () -> {
            expenseService.createExpanse(newExpenseRequest);
        });

        verify(expenseRepository, never()).save(any(Expense.class));
        verify(expenseMapper, never()).toResponse(any(Expense.class));
    }

}
