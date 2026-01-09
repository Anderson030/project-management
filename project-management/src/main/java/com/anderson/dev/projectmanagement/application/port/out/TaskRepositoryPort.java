package com.anderson.dev.projectmanagement.application.port.out;

import com.anderson.dev.projectmanagement.domain.model.Task;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {
    Optional<Task> findById(UUID id);

    List<Task> findByProjectId(UUID projectId);

    boolean existsActiveTask(UUID projectId);

    void save(Task task);
}
