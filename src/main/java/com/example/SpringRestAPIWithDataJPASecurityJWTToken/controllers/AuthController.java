package com.example.SpringRestAPIWithDataJPASecurityJWTToken.controllers;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.security.JWTUtil;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.RegistrationService;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(
            PersonValidator personValidator,
            JWTUtil jwtUtil,
            RegistrationService registrationService,
            ModelMapper modelMapper,
            AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage() {

    }


}
