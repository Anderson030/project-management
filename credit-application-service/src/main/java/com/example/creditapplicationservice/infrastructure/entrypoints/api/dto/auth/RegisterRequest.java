package com.example.creditapplicationservice.infrastructure.entrypoints.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // El usuario dirá si es ROLE_AFILIADO, etc.
}