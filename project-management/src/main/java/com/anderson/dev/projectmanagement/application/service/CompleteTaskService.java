package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.CompleteTaskUseCase;
import com.anderson.dev.projectmanagement.application.port.out.AuditLogPort;
import com.anderson.dev.projectmanagement.application.port.out.NotificationPort;
import com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Task;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompleteTaskService implements CompleteTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;

    public CompleteTaskService(
            TaskRepositoryPort taskRepository,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort
    ) {
        this.taskRepository = taskRepository;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public void complete(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (task.isCompleted()) {
            throw new IllegalStateException("Task already completed");
        }

        task.complete();
        taskRepository.save(task);

        auditLogPort.register("TASK_COMPLETED", taskId);
        notificationPort.notify("Task completed");
    }
}
