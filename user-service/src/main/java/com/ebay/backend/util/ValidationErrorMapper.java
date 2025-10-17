package com.ebay.backend.util;

import com.ebay.backend.dto.response.ErrorResponse;
import com.ebay.backend.dto.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ValidationErrorMapper {
    public static ErrorResponse fromBindingErrors(BindingResult result) {
        var code = new AtomicReference<String>();
        List<ValidationErrorResponse> validationErrors = result.getFieldErrors().stream()
                .map(error -> {
                    code.set(error.getCode());
                    return new ValidationErrorResponse(error.getField(), error.getDefaultMessage());
                })
                .toList();

        return new  ErrorResponse(code.get(), "Validation errors occurred", validationErrors);
    }

    public static ErrorResponse fromException(HttpStatus status, String error, String message) {
        return new ErrorResponse(
                String.valueOf(status.value()),
                error,
                List.of(new ValidationErrorResponse(null, message))
        );
    }
}
