package com.anderson.dev.projectmanagement.infrastructure.security;

import com.anderson.dev.projectmanagement.application.port.out.CurrentUserPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserAdapter implements CurrentUserPort {

    @Override
    public UUID getCurrentUserId() {
        return (UUID) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
