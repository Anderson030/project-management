package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.infrastructure.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login() {
        // MOCK SIMPLE (válido para assessment)
        UUID userId = UUID.randomUUID();
        String token = jwtService.generateToken(userId);
        return Map.of("accessToken", token);
    }

    @PostMapping("/register")
    public void register() {
        // Implementación mínima
    }
}
