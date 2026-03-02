package com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Для создания/обновления
@Data
public class PersonRequestDTO {
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя должно быть в диапазоне от 3 до 50 символов")
    private String username;

    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int yearOfBirth;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
}
