package com.example.creditapplicationservice.domain.port.out;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationRepositoryPort {
    CreditApplication save(CreditApplication application);

    List<CreditApplication> findAll(); // Para Admin
    List<CreditApplication> findAllByStatus(ApplicationStatus status); // Para Analista
    List<CreditApplication> findAllByAffiliateDocument(String document);
}
