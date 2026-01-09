package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.ListTasksUseCase;
import com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListTasksService implements ListTasksUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public ListTasksService(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public List<Task> listTasks(UUID projectId) {
        return taskRepositoryPort.findByProjectId(projectId);
    }
}
