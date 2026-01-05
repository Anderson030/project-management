package com.anderson.dev.projectmanagement.domain.model;

import java.util.UUID;

public class Task {

    private UUID id;
    private UUID projectId;
    private String title;
    private boolean completed;
    private boolean deleted;

    public Task(UUID id, UUID projectId, String title) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = false;
        this.deleted = false;
    }

    public void complete() {
        if (completed) {
            throw new IllegalStateException("Task already completed");
        }
        this.completed = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
