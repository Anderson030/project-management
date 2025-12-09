package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.repository;

import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationJpaRepository extends JpaRepository<CreditApplicationEntity, Long> {
    List<CreditApplicationEntity> findByAffiliate_Document(String document);
    List<CreditApplicationEntity> findByStatus(ApplicationStatus status);
}
