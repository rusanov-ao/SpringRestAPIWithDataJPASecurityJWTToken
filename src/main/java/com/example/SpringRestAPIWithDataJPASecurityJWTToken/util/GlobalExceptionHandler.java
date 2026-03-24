package com.example.SpringRestAPIWithDataJPASecurityJWTToken.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * Чтобы ошибки возвращались в формате JSON (а не HTML)
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<PersonErrorResponse> handleNotFound(PersonNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new PersonErrorResponse(e.getMessage(), System.currentTimeMillis()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<PersonErrorResponse> handleConflict(UsernameAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new PersonErrorResponse(e.getMessage(), System.currentTimeMillis()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<PersonErrorResponse> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new PersonErrorResponse(e.getMessage(), System.currentTimeMillis()));
    }

    // ✅ Обработка ошибок валидации (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PersonErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new PersonErrorResponse(errors, System.currentTimeMillis()));
    }

    // ✅ Обработка остальных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PersonErrorResponse> handleGeneral(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PersonErrorResponse("Произошла ошибка на сервере", System.currentTimeMillis()));
    }
}
