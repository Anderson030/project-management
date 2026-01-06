package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.application.port.in.CompleteTaskUseCase;
import com.anderson.dev.projectmanagement.application.port.in.CreateTaskUseCase;
import com.anderson.dev.projectmanagement.presentation.controller.dto.CreateTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final com.anderson.dev.projectmanagement.application.port.in.ListTasksUseCase listTasksUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            CompleteTaskUseCase completeTaskUseCase,
            com.anderson.dev.projectmanagement.application.port.in.ListTasksUseCase listTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.listTasksUseCase = listTasksUseCase;
    }

    @GetMapping("/api/tasks")
    public java.util.List<com.anderson.dev.projectmanagement.domain.model.Task> list(@RequestParam UUID projectId) {
        return listTasksUseCase.list(projectId);
    }

    @PostMapping("/api/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateTaskRequest request) {
        createTaskUseCase.create(request.projectId(), request.title());
    }

    @PatchMapping("/api/tasks/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void complete(@PathVariable UUID id) {
        completeTaskUseCase.complete(id);
    }
}
