package com.anderson.dev.projectmanagement.presentation.controller;

import com.anderson.dev.projectmanagement.application.port.in.ActivateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.in.CreateProjectCommand;
import com.anderson.dev.projectmanagement.application.port.in.CreateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.in.ListProjectsUseCase;
import com.anderson.dev.projectmanagement.application.port.out.CurrentUserPort;
import com.anderson.dev.projectmanagement.domain.model.Project;
import com.anderson.dev.projectmanagement.presentation.controller.dto.CreateProjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ActivateProjectUseCase activateProjectUseCase;
    private final ListProjectsUseCase listProjectsUseCase;
    private final CurrentUserPort currentUserPort;

    public ProjectController(
            CreateProjectUseCase createProjectUseCase,
            ActivateProjectUseCase activateProjectUseCase,
            ListProjectsUseCase listProjectsUseCase,
            CurrentUserPort currentUserPort) {
        this.createProjectUseCase = createProjectUseCase;
        this.activateProjectUseCase = activateProjectUseCase;
        this.listProjectsUseCase = listProjectsUseCase;
        this.currentUserPort = currentUserPort;
    }

    @GetMapping
    public List<Project> list() {
        return listProjectsUseCase.listProjects(currentUserPort.getCurrentUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody CreateProjectRequest request) {
        return createProjectUseCase.create(
                new CreateProjectCommand(
                        currentUserPort.getCurrentUserId(),
                        request.name()));
    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable UUID id) {
        activateProjectUseCase.activate(id);
    }
}
