package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.application.port.in.ActivateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.in.CreateProjectCommand;
import com.anderson.dev.projectmanagement.application.port.in.CreateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.out.CurrentUserPort;
import com.anderson.dev.projectmanagement.presentation.controller.dto.CreateProjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ActivateProjectUseCase activateProjectUseCase;
    private final CurrentUserPort currentUserPort;

    public ProjectController(
            CreateProjectUseCase createProjectUseCase,
            ActivateProjectUseCase activateProjectUseCase,
            CurrentUserPort currentUserPort
    ) {
        this.createProjectUseCase = createProjectUseCase;
        this.activateProjectUseCase = activateProjectUseCase;
        this.currentUserPort = currentUserPort;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody CreateProjectRequest request) {
        return createProjectUseCase.create(
                new CreateProjectCommand(
                        currentUserPort.getCurrentUserId(),
                        request.name()
                )
        );
    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable UUID id) {
        activateProjectUseCase.activate(id);
    }
}
