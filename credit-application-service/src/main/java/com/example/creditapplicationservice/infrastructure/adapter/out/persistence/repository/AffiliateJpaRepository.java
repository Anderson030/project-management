package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.repository;

import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliateJpaRepository extends JpaRepository<AffiliateEntity, Long> {
}
