package com.anderson.dev.projectmanagement.application.port.in;

import java.util.UUID;

public record CreateProjectCommand(
        UUID ownerId,
        String name
) {}
