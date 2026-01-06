package com.anderson.dev.projectmanagement.infrastructure.config;

import com.anderson.dev.projectmanagement.application.port.out.UserRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner initAdmin(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User(
                        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), // Consistent ID
                        "admin",
                        "admin@example.com",
                        passwordEncoder.encode("123"));
                userRepository.save(admin);
                System.out.println("ADMIN user seeded successfully.");
            }
        };
    }
}
