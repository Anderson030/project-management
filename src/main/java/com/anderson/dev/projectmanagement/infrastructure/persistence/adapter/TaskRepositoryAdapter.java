package com.anderson.dev.projectmanagement.infrastructure.persistence.adapter;

import com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Task;
import com.anderson.dev.projectmanagement.infrastructure.persistence.entity.ProjectEntity;
import com.anderson.dev.projectmanagement.infrastructure.persistence.entity.TaskEntity;
import com.anderson.dev.projectmanagement.infrastructure.persistence.repository.JpaTaskRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository repository;
    private final jakarta.persistence.EntityManager entityManager;

    public TaskRepositoryAdapter(JpaTaskRepository repository, jakarta.persistence.EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Task> findById(UUID taskId) {
        return repository.findById(taskId).map(this::toDomain);
    }

    @Override
    public boolean existsActiveTask(UUID projectId) {
        return repository.existsByProject_IdAndDeletedFalse(projectId);
    }

    @Override
    public void save(Task task) {
        repository.save(toEntity(task));
    }

    @Override
    public int countByProjectId(UUID projectId) {
        return repository.countByProject_IdAndDeletedFalse(projectId);
    }

    @Override
    public java.util.List<Task> findAllByProjectId(UUID projectId) {
        return repository.findAllByProject_IdAndDeletedFalse(projectId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Task toDomain(TaskEntity entity) {
        Task task = new Task(
                entity.getId(),
                entity.getProject().getId(),
                entity.getTitle());
        if (entity.isCompleted()) {
            task.complete();
        }
        return task;
    }

    private TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());

        // Use getReference to avoid DB hit, just proxy for FK
        ProjectEntity projectRef = entityManager.getReference(ProjectEntity.class, task.getProjectId());
        entity.setProject(projectRef);

        entity.setTitle(task.getTitle());
        entity.setCompleted(task.isCompleted());
        entity.setDeleted(task.isDeleted());
        return entity;
    }
}
