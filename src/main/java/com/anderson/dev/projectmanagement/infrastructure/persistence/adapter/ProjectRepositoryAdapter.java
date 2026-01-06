package com.anderson.dev.projectmanagement.infrastructure.persistence.adapter;

import com.anderson.dev.projectmanagement.application.port.out.ProjectRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Project;
import com.anderson.dev.projectmanagement.infrastructure.persistence.entity.ProjectEntity;
import com.anderson.dev.projectmanagement.infrastructure.persistence.repository.JpaProjectRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {

    private final JpaProjectRepository repository;

    public ProjectRepositoryAdapter(JpaProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Project> findById(java.util.UUID projectId) {
        return repository.findById(projectId).map(this::toDomain);
    }

    @Override
    public void save(Project project) {
        repository.save(toEntity(project));
    }

    @Override
    public List<Project> findAllByOwnerId(UUID ownerId) {
        return repository.findAllByOwnerId(ownerId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Project toDomain(ProjectEntity entity) {
        Project project = new Project(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName());
        if (entity.getStatus() == ProjectEntity.Status.ACTIVE) {
            project.activate();
        }
        return project;
    }

    private ProjectEntity toEntity(Project project) {
        ProjectEntity entity = new ProjectEntity();
        entity.setId(project.getId());
        entity.setOwnerId(project.getOwnerId());
        entity.setName(project.getName());
        entity.setStatus(
                project.getStatus() == Project.Status.ACTIVE
                        ? ProjectEntity.Status.ACTIVE
                        : ProjectEntity.Status.DRAFT);
        entity.setDeleted(project.isDeleted());
        return entity;
    }
}
