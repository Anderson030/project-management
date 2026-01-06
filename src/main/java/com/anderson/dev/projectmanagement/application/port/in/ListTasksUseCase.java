package com.anderson.dev.projectmanagement.application.port.in;

import com.anderson.dev.projectmanagement.domain.model.Task;
import java.util.List;
import java.util.UUID;

public interface ListTasksUseCase {
    List<Task> list(UUID projectId);
}
