package com.dhentech.PersonalFinancialControlAPI.application.web.mapper;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.ExpenseResponse;
import com.dhentech.PersonalFinancialControlAPI.domain.model.expense.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    private final CategoryMapper categoryMapper;

    public ExpenseMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ExpenseResponse toResponse(Expense expense) {
        if (expense == null) {
            return null;
        }

        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                categoryMapper.toResponse(expense.getCategory())
        );
    }

}
