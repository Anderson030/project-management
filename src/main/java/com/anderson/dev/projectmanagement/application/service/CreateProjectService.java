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
    @org.springframework.transaction.annotation.Transactional
    public UUID create(CreateProjectCommand command) {
        System.out.println("CreateProjectService: Creating project for owner: " + command.ownerId());
        Project project = new Project(
                UUID.randomUUID(),
                command.ownerId(),
                command.name());
        projectRepository.save(project);
        System.out.println("CreateProjectService: Project saved with ID: " + project.getId());
        return project.getId();
    }
}
