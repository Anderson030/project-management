package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.application.port.in.CreateTaskUseCase;
import com.anderson.dev.projectmanagement.application.port.in.ListTasksUseCase;
import com.anderson.dev.projectmanagement.domain.model.Task;
import com.anderson.dev.projectmanagement.presentation.controller.dto.CreateTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final ListTasksUseCase listTasksUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            CompleteTaskUseCase completeTaskUseCase,
            ListTasksUseCase listTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.listTasksUseCase = listTasksUseCase;
    }

    @GetMapping("/api/projects/{projectId}/tasks")
    public List<Task> list(@PathVariable UUID projectId) {
        return listTasksUseCase.listTasks(projectId);
    }

    @PostMapping("/api/projects/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @PathVariable UUID projectId,
            @RequestBody CreateTaskRequest request) {
        createTaskUseCase.create(projectId, request.title());
    }

    @PatchMapping("/api/tasks/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void complete(@PathVariable UUID id) {
        completeTaskUseCase.complete(id);
    }
}
