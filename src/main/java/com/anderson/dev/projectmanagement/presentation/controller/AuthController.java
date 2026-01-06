package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.infrastructure.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final com.anderson.dev.projectmanagement.application.port.in.RegisterUserUseCase registerUserUseCase;
    private final com.anderson.dev.projectmanagement.application.port.in.LoginUseCase loginUseCase;

    public AuthController(
            JwtService jwtService,
            com.anderson.dev.projectmanagement.application.port.in.RegisterUserUseCase registerUserUseCase,
            com.anderson.dev.projectmanagement.application.port.in.LoginUseCase loginUseCase) {
        this.jwtService = jwtService;
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        System.out.println("Login attempt: " + request.username());
        UUID userId = loginUseCase.login(request.username(), request.password());
        String token = jwtService.generateToken(userId);
        return Map.of("accessToken", token);
    }

    @PostMapping("/register")
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest request) {
        registerUserUseCase.register(request.username(), request.email(), request.password());
    }

    public record LoginRequest(String username, String password) {
    }

    public record RegisterRequest(String username, String email, String password) {
    }
}
