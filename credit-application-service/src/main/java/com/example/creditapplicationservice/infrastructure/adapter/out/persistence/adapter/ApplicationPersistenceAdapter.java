package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.adapter;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;
import com.example.creditapplicationservice.domain.port.out.ApplicationRepositoryPort;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.mapper.ApplicationEntityMapper;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.repository.ApplicationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationPersistenceAdapter implements ApplicationRepositoryPort {

    private final ApplicationJpaRepository applicationJpaRepository;
    private final ApplicationEntityMapper applicationEntityMapper;

    @Override
    public CreditApplication save(CreditApplication application) {
        CreditApplicationEntity applicationEntity = applicationEntityMapper.toEntity(application);

        if (application.getAffiliateId() != null) {
            AffiliateEntity affiliateRef = AffiliateEntity.builder()
                    .id(application.getAffiliateId())
                    .build();
            applicationEntity.setAffiliate(affiliateRef);
        }

        if (applicationEntity.getEvaluation() != null) {
            applicationEntity.getEvaluation().setApplication(applicationEntity);
        }

        CreditApplicationEntity savedEntity = applicationJpaRepository.save(applicationEntity);
        return applicationEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<CreditApplication> findAll() {
        return applicationJpaRepository.findAll().stream()
                .map(applicationEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<CreditApplication> findAllByStatus(ApplicationStatus status) {
        return applicationJpaRepository.findByStatus(status).stream()
                .map(applicationEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<CreditApplication> findAllByAffiliateDocument(String document) {
        return applicationJpaRepository.findByAffiliate_Document(document).stream()
                .map(applicationEntityMapper::toDomain)
                .toList();
    }
}