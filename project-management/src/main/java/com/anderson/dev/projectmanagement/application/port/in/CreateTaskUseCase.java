package com.anderson.dev.projectmanagement.application.port.in;

import java.util.UUID;

public interface CreateTaskUseCase {
    void create(UUID projectId, String title);
}
