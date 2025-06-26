package com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse;

import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.ExpenseResponse;
import com.dhentech.PersonalFinancialControlAPI.application.web.controller.expanse.dto.NewExpenseRequest;
import com.dhentech.PersonalFinancialControlAPI.domain.service.expanse.ExpenseService;
import com.dhentech.PersonalFinancialControlAPI.infrastructure.repository.jpa.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expenses", description = "Endpoints for managing financial expenses")
public class ExpanseController {

    private final ExpenseService expenseService;

    public ExpanseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @Operation(summary = "Create a new expense", description = "Registers a new expense and associates it with an existing category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided (e.g., validation error)"),
            @ApiResponse(responseCode = "404", description = "Category specified in the request not found")
    })
    public ResponseEntity<ExpenseResponse> createNewExpense(@Valid @RequestBody NewExpenseRequest request) {
        ExpenseResponse response = this.expenseService.createExpanse(request);
        return ResponseEntity.ok(response);
    }
}
