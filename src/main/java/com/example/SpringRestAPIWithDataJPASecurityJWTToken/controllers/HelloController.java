package com.example.SpringRestAPIWithDataJPASecurityJWTToken.controllers;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonResponseDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.security.PersonDetails;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.AdminService;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.HelloResponse;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/showUserInfo")
    public ResponseEntity<UserInfoResponse> showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // ✅ Безопасное приведение типов (Java 16+ Pattern Matching)
        if (authentication.getPrincipal() instanceof PersonDetails personDetails) {
            return ResponseEntity.ok(
                    new UserInfoResponse(
                            personDetails.getUsername(),
                            personDetails.getPerson().getRole()
                    )
            );
        }
        return ResponseEntity.ok(new UserInfoResponse("Anonymous", null));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<PersonResponseDTO>> adminPage() {
        // ✅ Вызываем реальный метод сервиса вместо doAdminStuff()
        List<PersonResponseDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
