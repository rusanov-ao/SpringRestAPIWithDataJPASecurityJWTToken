package com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationDTO {

    @NotEmpty
    @Size(min = 3, max = 50, message = "Username должен быть в диапозоне от 3 до 50 символов")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
