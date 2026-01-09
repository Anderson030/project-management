package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.ActivateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.out.ProjectRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Project;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivateProjectService implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort taskRepository;

    public ActivateProjectService(
            ProjectRepositoryPort projectRepository,
            com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void activate(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new com.anderson.dev.projectmanagement.domain.exception.BusinessException(
                        "Project not found"));

        var tasks = taskRepository.findByProjectId(projectId);
        if (tasks.isEmpty()) {
            throw new com.anderson.dev.projectmanagement.domain.exception.BusinessException(
                    "Cannot activate project without tasks");
        }

        project.activate();
        projectRepository.save(project);
    }
}
