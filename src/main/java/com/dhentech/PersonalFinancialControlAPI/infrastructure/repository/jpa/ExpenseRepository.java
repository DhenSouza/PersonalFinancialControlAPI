package com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa;

import com.dhentech.PersonalFinancialControlAPI.domain.model.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
