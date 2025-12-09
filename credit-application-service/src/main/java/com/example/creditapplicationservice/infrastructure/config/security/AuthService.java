package com.example.creditapplicationservice.infrastructure.config.security;

import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import com.example.creditapplicationservice.infrastructure.entrypoints.api.dto.auth.AuthRequest;
import com.example.creditapplicationservice.infrastructure.entrypoints.api.dto.auth.AuthResponse;
import com.example.creditapplicationservice.infrastructure.entrypoints.api.dto.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Handles user registration.
     */
    public AuthResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                // IMPORTANT: Encrypt password before saving
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        // Generate token so the user doesn't have to log in again
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Handles user authentication.
     */
    public AuthResponse authenticate(AuthRequest request) {
        // Spring's AuthenticationManager handles the password validation
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // If authentication is successful, find the user and generate a token
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
