package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.CreateTaskUseCase;
import com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Task;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateTaskService implements CreateTaskUseCase {

    private final TaskRepositoryPort taskRepository;

    public CreateTaskService(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void create(UUID projectId, String title) {
        System.out.println("CreateTaskService: Creating task for project: " + projectId);
        Task task = new Task(UUID.randomUUID(), projectId, title);
        taskRepository.save(task);
        System.out.println("CreateTaskService: Task saved with ID: " + task.getId());
    }
}
