package com.example.creditapplicationservice.application.service;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;
import com.example.creditapplicationservice.domain.port.in.GetCreditApplicationsUseCase;
import com.example.creditapplicationservice.domain.port.out.ApplicationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCreditApplicationsUseCaseImpl implements GetCreditApplicationsUseCase {

    private final ApplicationRepositoryPort applicationRepositoryPort;

    public GetCreditApplicationsUseCaseImpl(ApplicationRepositoryPort applicationRepositoryPort) {
        this.applicationRepositoryPort = applicationRepositoryPort;
    }

    @Override
    public List<CreditApplication> getByRole(String username, String role) {

        // 1. ADMIN
        if ("ROLE_ADMIN".equals(role)) {
            return applicationRepositoryPort.findAll();
        }

        // 2. Analista
        if ("ROLE_ANALISTA".equals(role)) {
            return applicationRepositoryPort.findAllByStatus(ApplicationStatus.PENDIENTE);
        }

        // 3. Affiliate
        if ("ROLE_AFILIADO".equals(role)) {
            // Asumimos que el 'username' del token es el DOCUMENTO del afiliado
            return applicationRepositoryPort.findAllByAffiliateDocument(username);
        }

        // if any, an empty list
        return List.of();
    }
}
