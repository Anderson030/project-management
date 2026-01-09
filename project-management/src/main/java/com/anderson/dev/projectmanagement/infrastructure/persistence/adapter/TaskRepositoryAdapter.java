package com.anderson.dev.projectmanagement.infrastructure.persistence.adapter;

import com.anderson.dev.projectmanagement.application.port.out.TaskRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.Task;
import com.anderson.dev.projectmanagement.infrastructure.persistence.entity.TaskEntity;
import com.anderson.dev.projectmanagement.infrastructure.persistence.repository.JpaTaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository repository;

    public TaskRepositoryAdapter(JpaTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Task> findById(UUID taskId) {
        return repository.findById(taskId).map(this::toDomain);
    }

    @Override
    public List<Task> findByProjectId(UUID projectId) {
        return repository.findByProjectIdAndDeletedFalse(projectId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsActiveTask(UUID projectId) {
        return repository.existsByProjectIdAndDeletedFalse(projectId);
    }

    @Override
    public void save(Task task) {
        repository.save(toEntity(task));
    }

    private Task toDomain(TaskEntity entity) {
        Task task = new Task(
                entity.getId(),
                entity.getProjectId(),
                entity.getTitle());
        if (entity.isCompleted()) {
            task.complete();
        }
        return task;
    }

    private TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setProjectId(task.getProjectId());
        entity.setTitle(task.getTitle());
        entity.setCompleted(task.isCompleted());
        entity.setDeleted(task.isDeleted());
        return entity;
    }
}
