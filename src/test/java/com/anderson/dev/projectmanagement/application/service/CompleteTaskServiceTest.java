package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.out.*;
import com.anderson.dev.projectmanagement.domain.exception.UnauthorizedAccessException;
import com.anderson.dev.projectmanagement.domain.model.Project;
import com.anderson.dev.projectmanagement.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompleteTaskServiceTest {

    @Mock
    private TaskRepositoryPort taskRepository;
    @Mock
    private ProjectRepositoryPort projectRepository;
    @Mock
    private AuditLogPort auditLogPort;
    @Mock
    private NotificationPort notificationPort;
    @Mock
    private CurrentUserPort currentUserPort;

    private CompleteTaskService service;

    private UUID taskId;
    private UUID projectId;
    private UUID ownerId;
    private UUID otherUserId;

    @BeforeEach
    void setUp() {
        service = new CompleteTaskService(
                taskRepository,
                projectRepository,
                auditLogPort,
                notificationPort,
                currentUserPort);
        taskId = UUID.randomUUID();
        projectId = UUID.randomUUID();
        ownerId = UUID.randomUUID();
        otherUserId = UUID.randomUUID();
    }

    @Test
    void CompleteTask_ShouldGenerateAuditAndNotification() {
        Task task = new Task(taskId, projectId, "Test Task");
        Project project = new Project(projectId, ownerId, "Test Project");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(ownerId);

        service.complete(taskId);

        assertTrue(task.isCompleted());
        verify(taskRepository).save(task);
        verify(auditLogPort).register("TASK_COMPLETED", taskId);
        verify(notificationPort).notify("Task completed");
    }

    @Test
    void CompleteTask_AlreadyCompleted_ShouldFail() {
        Task task = new Task(taskId, projectId, "Test Task");
        task.complete();
        Project project = new Project(projectId, ownerId, "Test Project");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(ownerId);

        assertThrows(IllegalStateException.class, () -> service.complete(taskId));

        verify(taskRepository, never()).save(any());
        verifyNoInteractions(auditLogPort, notificationPort);
    }

    @Test
    void CompleteTask_ByNonOwner_ShouldFail() {
        Task task = new Task(taskId, projectId, "Test Task");
        Project project = new Project(projectId, ownerId, "Test Project");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(otherUserId);

        assertThrows(UnauthorizedAccessException.class, () -> service.complete(taskId));

        verify(taskRepository, never()).save(any());
        verifyNoInteractions(auditLogPort, notificationPort);
    }
}
