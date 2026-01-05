package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.infrastructure.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    // USER MOCK FIJO (IMPORTANTE)
    private static final UUID FIXED_USER_ID =
            UUID.fromString("11111111-1111-1111-1111-111111111111");

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login() {
        String token = jwtService.generateToken(FIXED_USER_ID);
        return Map.of("accessToken", token);
    }

    @PostMapping("/register")
    public void register() {
        // No requerido para assessment
    }
}
