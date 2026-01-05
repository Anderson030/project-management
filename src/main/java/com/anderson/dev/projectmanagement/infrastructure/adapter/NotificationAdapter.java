package com.anderson.dev.projectmanagement.infrastructure.adapter;

import com.anderson.dev.projectmanagement.application.port.out.NotificationPort;
import org.springframework.stereotype.Component;

@Component
public class NotificationAdapter implements NotificationPort {

    @Override
    public void notify(String message) {

        System.out.println("[NOTIFICATION] " + message);
    }
}
