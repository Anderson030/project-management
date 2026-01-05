package com.anderson.dev.projectmanagement.application.port.in;

import java.util.UUID;

public interface ActivateProjectUseCase {
    void activate(UUID projectId);
}
