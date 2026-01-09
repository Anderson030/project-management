package com.anderson.dev.projectmanagement.infrastructure.persistence.repository;

import com.anderson.dev.projectmanagement.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, UUID> {

    boolean existsByProjectIdAndDeletedFalse(UUID projectId);

    java.util.List<TaskEntity> findByProjectIdAndDeletedFalse(UUID projectId);
}
