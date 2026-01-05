package com.anderson.dev.projectmanagement.infrastructure.adapter;

import com.anderson.dev.projectmanagement.application.port.out.AuditLogPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuditLogAdapter implements AuditLogPort {

    @Override
    public void register(String action, UUID entityId) {

        System.out.println("[AUDIT] " + action + " - " + entityId);
    }
}
