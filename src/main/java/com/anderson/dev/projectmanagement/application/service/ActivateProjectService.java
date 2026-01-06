package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.ActivateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.out.*;
import com.anderson.dev.projectmanagement.domain.exception.ProjectValidationException;
import com.anderson.dev.projectmanagement.domain.exception.UnauthorizedAccessException;
import com.anderson.dev.projectmanagement.domain.model.Project;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivateProjectService implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;
    private final CurrentUserPort currentUserPort;

    public ActivateProjectService(
            ProjectRepositoryPort projectRepository,
            TaskRepositoryPort taskRepository,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort,
            CurrentUserPort currentUserPort) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public void activate(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectValidationException("Project not found"));

        UUID currentUserId = currentUserPort.getCurrentUserId();
        if (!project.getOwnerId().equals(currentUserId)) {
            throw new UnauthorizedAccessException("Only the project owner can activate the project");
        }

        int taskCount = taskRepository.countByProjectId(projectId);
        if (taskCount == 0) {
            throw new ProjectValidationException("Cannot activate a project without tasks");
        }

        project.activate();
        projectRepository.save(project);

        auditLogPort.register("PROJECT_ACTIVATED", projectId);
        notificationPort.notify("Project activated: " + projectId);
    }
}
