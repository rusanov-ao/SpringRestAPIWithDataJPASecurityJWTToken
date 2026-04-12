package com.example.SpringRestAPIWithDataJPASecurityJWTToken.controllers;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.AdminService;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.HelloResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final AdminService adminService;

    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public ResponseEntity<HelloResponse> sayHello() {
        return ResponseEntity.ok(new HelloResponse("Hello, World!"));
    }

}
