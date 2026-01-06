package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final JwtService jwtService;

    // USER MOCK (válido para el assessment)
    private static final UUID FIXED_USER_ID =
            UUID.fromString("11111111-1111-1111-1111-111111111111");

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login() {
        String token = jwtService.generateToken(FIXED_USER_ID);
        return ResponseEntity.ok(Map.of("accessToken", token));
    }
}
