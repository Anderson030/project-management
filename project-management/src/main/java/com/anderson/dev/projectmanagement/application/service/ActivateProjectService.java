package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.ActivateProjectUseCase;
import com.anderson.dev.projectmanagement.application.port.out.ProjectRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Project;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivateProjectService implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;

    public ActivateProjectService(ProjectRepositoryPort projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void activate(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        project.activate();
        projectRepository.save(project);
    }
}
