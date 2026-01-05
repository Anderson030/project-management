package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.out.*;
import com.anderson.dev.projectmanagement.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompleteTaskServiceTest {

    private TaskRepositoryPort taskRepository;
    private AuditLogPort auditLogPort;
    private NotificationPort notificationPort;

    private CompleteTaskService service;

    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepositoryPort.class);
        auditLogPort = mock(AuditLogPort.class);
        notificationPort = mock(NotificationPort.class);

        service = new CompleteTaskService(
                taskRepository,
                auditLogPort,
                notificationPort
        );

        taskId = UUID.randomUUID();
    }

    @Test
    void CompleteTask_ShouldGenerateAuditAndNotification() {
        Task task = new Task(taskId, UUID.randomUUID(), "Test task");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        service.complete(taskId);

        assertTrue(task.isCompleted());
        verify(taskRepository).save(task);
        verify(auditLogPort).register("TASK_COMPLETED", taskId);
        verify(notificationPort).notify("Task completed");
    }

    @Test
    void CompleteTask_AlreadyCompleted_ShouldFail() {
        Task task = new Task(taskId, UUID.randomUUID(), "Test task");
        task.complete();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(
                IllegalStateException.class,
                () -> service.complete(taskId)
        );

        verify(taskRepository, never()).save(any());
    }
}
