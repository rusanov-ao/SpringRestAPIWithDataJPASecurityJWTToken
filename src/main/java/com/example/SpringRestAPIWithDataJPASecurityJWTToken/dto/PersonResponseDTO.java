package com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Role;

public record PersonResponseDTO(
        Long id,
        String username,
        int yearOfBirth,
        Role role
) {
}
