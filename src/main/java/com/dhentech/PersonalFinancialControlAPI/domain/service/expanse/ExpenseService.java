package com.dhentech.PersonalFinancialControlAPI.domain.service.expanse;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.ExpenseResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.NewExpenseRequest;

public interface ExpenseService {
    ExpenseResponse createExpanse(NewExpenseRequest newExpenseRequest);
}
