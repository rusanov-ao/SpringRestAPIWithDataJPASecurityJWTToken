package com.example.SpringRestAPIWithDataJPASecurityJWTToken.util;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message); // Обязательно передаём сообщение
    }
}
