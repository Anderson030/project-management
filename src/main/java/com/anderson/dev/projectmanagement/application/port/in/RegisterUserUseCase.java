package com.anderson.dev.projectmanagement.application.port.in;

public interface RegisterUserUseCase {
    void register(String username, String email, String password);
}
