package com.anderson.dev.projectmanagement.presentation.controller.dto;

import java.util.UUID;

public record CreateTaskRequest(UUID projectId, String title) {
}
