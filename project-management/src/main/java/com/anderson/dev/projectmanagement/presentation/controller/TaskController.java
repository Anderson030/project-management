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

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            CompleteTaskUseCase completeTaskUseCase
    ) {
        this.createTaskUseCase = createTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
    }

    @PostMapping("/api/projects/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @PathVariable UUID projectId,
            @RequestBody CreateTaskRequest request
    ) {
        createTaskUseCase.create(projectId, request.title());
    }

    @PatchMapping("/api/tasks/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void complete(@PathVariable UUID id) {
        completeTaskUseCase.complete(id);
    }
}
