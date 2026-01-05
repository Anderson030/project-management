package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.CreateProjectCommand;
import com.anderson.dev.projectmanagement.application.port.in.CreateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.out.ProjectRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Project;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;

    public CreateProjectService(ProjectRepositoryPort projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public UUID create(CreateProjectCommand command) {
        Project project = new Project(
                UUID.randomUUID(),
                command.ownerId(),
                command.name()
        );
        projectRepository.save(project);
        return project.getId();
    }
}
