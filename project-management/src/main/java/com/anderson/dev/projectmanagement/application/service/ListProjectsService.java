package com.anderson.dev.projectmanagement.application.service;

import com.anderson.dev.projectmanagement.application.port.in.ListProjectsUseCase;
import com.anderson.dev.projectmanagement.application.port.out.ProjectRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListProjectsService implements ListProjectsUseCase {

    private final ProjectRepositoryPort projectRepository;

    public ListProjectsService(ProjectRepositoryPort projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> listProjects(UUID ownerId) {
        return projectRepository.findAllByOwnerId(ownerId);
    }
}
