package com.example.SpringRestAPIWithDataJPASecurityJWTToken.util;

/**
 * это стандартизированный формат для всех ошибок в моем API.
 */
public record PersonErrorResponse(
        String message,
        long timestamp
) {}
