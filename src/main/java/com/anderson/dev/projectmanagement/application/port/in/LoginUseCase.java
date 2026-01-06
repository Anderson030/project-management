package com.anderson.dev.projectmanagement.application.port.in;

import java.util.UUID;

public interface LoginUseCase {
    UUID login(String username, String password);
}
