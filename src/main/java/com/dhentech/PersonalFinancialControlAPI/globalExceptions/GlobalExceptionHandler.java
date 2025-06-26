package com.dhentech.PersonalFinancialControlAPI.globalExceptions;

import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.BusinessRuleException;
import com.dhentech.PersonalFinancialControlAPI.domain.exceptions.ResourceNotFoundException;
import com.dhentech.PersonalFinancialControlAPI.globalExceptions.dto.ErrorField;
import com.dhentech.PersonalFinancialControlAPI.globalExceptions.dto.ErrorResponse;
import com.dhentech.PersonalFinancialControlAPI.globalExceptions.enums.ProblemType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        String detail = ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ProblemType.RESOURCE_NOT_FOUND_TITLE.getPath(),
                ProblemType.RESOURCE_NOT_FOUND_TITLE.getTitle(),
                detail,
                request.getRequestURI(),
                ProblemType.RESOURCE_NOT_FOUND_TITLE.getMessage()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ErrorField> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorField(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ProblemType.METHOD_ARGUMENT_NOT_VALID.getPath(),
                ProblemType.METHOD_ARGUMENT_NOT_VALID.getTitle(),
                ex.getMessage(),
                request.getRequestURI(),
                ProblemType.METHOD_ARGUMENT_NOT_VALID.getMessage(),
                fieldErrors
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(
            BusinessRuleException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ProblemType.BUSINESS_RULE.getPath(),
                ProblemType.BUSINESS_RULE.getTitle(),
                ex.getMessage(),
                request.getRequestURI(),
                ProblemType.BUSINESS_RULE.getMessage());

        return new ResponseEntity<>(errorResponse, status);
    }
}
