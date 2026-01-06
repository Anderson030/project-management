package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.CompleteTaskUseCase;
import com.anderson.dev.projectmanagement.application.port.out.*;
import com.anderson.dev.projectmanagement.domain.exception.UnauthorizedAccessException;
import com.anderson.dev.projectmanagement.domain.model.Project;
import com.anderson.dev.projectmanagement.domain.model.Task;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompleteTaskService implements CompleteTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;
    private final CurrentUserPort currentUserPort;

    public CompleteTaskService(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort,
            CurrentUserPort currentUserPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public void complete(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found for task"));

        UUID currentUserId = currentUserPort.getCurrentUserId();
        if (!project.getOwnerId().equals(currentUserId)) {
            throw new UnauthorizedAccessException("Only the project owner can complete tasks");
        }

        if (task.isCompleted()) {
            throw new IllegalStateException("Task already completed");
        }

        task.complete();
        taskRepository.save(task);

        auditLogPort.register("TASK_COMPLETED", taskId);
        notificationPort.notify("Task completed");
    }
}
