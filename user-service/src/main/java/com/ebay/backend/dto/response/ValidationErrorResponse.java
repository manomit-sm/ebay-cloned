package com.ebay.backend.dto.response;

public record ValidationErrorResponse(String field, String message) {
}
