package com.anderson.dev.projectmanagement.domain.model;

import java.util.UUID;

public class Project {

    private UUID id;
    private UUID ownerId;
    private String name;
    private Status status;
    private boolean deleted;

    public enum Status {
        DRAFT,
        ACTIVE
    }

    public Project(UUID id, UUID ownerId, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.status = Status.DRAFT;
        this.deleted = false;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
