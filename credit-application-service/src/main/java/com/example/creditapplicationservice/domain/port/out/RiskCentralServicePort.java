package com.example.creditapplicationservice.domain.port.out;

import com.example.creditapplicationservice.infrastructure.adapter.in.dto.RiskCentralResponse;

import java.math.BigDecimal;

public interface RiskCentralServicePort {
    RiskCentralResponse evaluateRisk(String document, BigDecimal amount, Integer term);
}
