package com.anderson.dev.projectmanagement.application.port.in;

import java.util.UUID;

public interface CreateProjectUseCase {
    UUID create(CreateProjectCommand command);
}
