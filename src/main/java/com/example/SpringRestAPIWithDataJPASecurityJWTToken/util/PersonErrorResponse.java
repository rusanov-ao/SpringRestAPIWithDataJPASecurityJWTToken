package com.example.SpringRestAPIWithDataJPASecurityJWTToken.util;

public record PersonErrorResponse(
        String message,
        long timestamp
) {}
