package com.example.SpringRestAPIWithDataJPASecurityJWTToken.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SOME_OTHER')")
    public void doAdminStuff() {
        System.out.println("This is the admin stuff");
    }
}
