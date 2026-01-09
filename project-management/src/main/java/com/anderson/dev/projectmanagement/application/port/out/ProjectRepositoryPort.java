package com.anderson.dev.projectmanagement.application.port.out;

import com.anderson.dev.projectmanagement.domain.model.Project;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepositoryPort {
    Optional<Project> findById(UUID id);

    java.util.List<Project> findAllByOwnerId(UUID ownerId);

    void save(Project project);
}
