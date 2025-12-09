package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.adapter;

import com.example.creditapplicationservice.domain.exception.ResourceNotFoundException;
import com.example.creditapplicationservice.domain.model.Affiliate;
import com.example.creditapplicationservice.domain.port.out.AffiliateRepositoryPort;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.mapper.AffiliateEntityMapper;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.repository.AffiliateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AffiliatePersistenceAdapter implements AffiliateRepositoryPort {

    private final AffiliateJpaRepository affiliateJpaRepository;
    private final AffiliateEntityMapper affiliateEntityMapper;

    @Override
    public Affiliate findById(Long id) {
        AffiliateEntity affiliate = affiliateJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Afiliado con id: "+id+" no encontrado"));
        return affiliateEntityMapper.toDomain(affiliate);
    }
}