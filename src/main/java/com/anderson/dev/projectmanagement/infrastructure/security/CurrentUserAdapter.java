package com.anderson.dev.projectmanagement.infrastructure.security;

import com.anderson.dev.projectmanagement.application.port.out.CurrentUserPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserAdapter implements CurrentUserPort {

    @Override
    public UUID getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return UUID.fromString((String) principal);
        }
        throw new IllegalStateException("User principal is not a String/UUID");
    }
}
