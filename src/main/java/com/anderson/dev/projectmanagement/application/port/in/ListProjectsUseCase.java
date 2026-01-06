package com.anderson.dev.projectmanagement.application.port.in;

import com.anderson.dev.projectmanagement.domain.model.Project;
import java.util.List;
import java.util.UUID;

public interface ListProjectsUseCase {
    List<Project> listProjects(UUID ownerId);
}
