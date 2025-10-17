package com.ebay.backend.dto.response;

import java.util.List;

public record ErrorResponse(String errorCode, String errorMessage, List<ValidationErrorResponse> errors) {
}
