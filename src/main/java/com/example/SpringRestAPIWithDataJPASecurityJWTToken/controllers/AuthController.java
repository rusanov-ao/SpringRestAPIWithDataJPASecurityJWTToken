package com.example.SpringRestAPIWithDataJPASecurityJWTToken.controllers;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.AuthResponseDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.AuthenticationDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonRequestDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.security.JWTUtil;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final JWTUtil jwtUtil;
    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            JWTUtil jwtUtil,
            RegistrationService registrationService,
            AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
    }

    // POST для регистрации, 201 Created при успехе
    @PostMapping("/registration")
    public ResponseEntity<AuthResponseDTO> performRegistration(@RequestBody @Valid PersonRequestDTO personDTO) {

        // Сервис возвращает Person, маппим в ResponseDTO
        var person = registrationService.register(personDTO);
        var token = jwtUtil.generateToken(person.getUsername());

        return ResponseEntity.status(201).body(new AuthResponseDTO(token));
    }

    // POST для логина, 200 OK при успехе
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> performLogin(
            @RequestBody @Valid AuthenticationDTO authDTO) {

        try {
            // Аутентификация через Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDTO.username(),
                            authDTO.password()
                    )
            );
        } catch (BadCredentialsException e) {
            // Выбрасываем исключение, а не возвращаем Map
            throw new BadCredentialsException("Неверное имя пользователя или пароль");
        }

        var token = jwtUtil.generateToken(authDTO.username());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
