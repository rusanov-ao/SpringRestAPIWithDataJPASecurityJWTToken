package com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        @NotEmpty(message = "Имя пользователя не может быть пустым")
        @Size(min = 3, max = 50, message = "Имя должно быть в диапазоне от 3 до 50 символов")
        String username,

        @NotEmpty(message = "Пароль не может быть пустым")
        String password
) {
    @Override
    public String toString() {
        return "AuthenticationDTO{username='" + username + "', password='***'}";
    }
}
