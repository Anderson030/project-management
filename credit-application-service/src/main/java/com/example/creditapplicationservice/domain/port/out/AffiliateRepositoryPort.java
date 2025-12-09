package com.example.creditapplicationservice.domain.port.out;

import com.example.creditapplicationservice.domain.model.Affiliate;

public interface AffiliateRepositoryPort {
    Affiliate findById(Long id);
}
