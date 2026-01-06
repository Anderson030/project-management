package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.LoginUseCase;
import com.anderson.dev.projectmanagement.application.port.out.UserRepositoryPort;
import com.anderson.dev.projectmanagement.domain.exception.UnauthorizedAccessException;
import com.anderson.dev.projectmanagement.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginUserService implements LoginUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUserService(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UUID login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedAccessException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedAccessException("Invalid credentials");
        }

        return user.getId();
    }
}
