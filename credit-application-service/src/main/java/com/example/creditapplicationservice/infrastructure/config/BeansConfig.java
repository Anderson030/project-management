package com.example.creditapplicationservice.infrastructure.config;

import com.example.creditapplicationservice.application.service.ApplicationService;
import com.example.creditapplicationservice.domain.port.in.ApplicationCreateUseCase;
import com.example.creditapplicationservice.domain.port.out.AffiliateRepositoryPort;
import com.example.creditapplicationservice.domain.port.out.ApplicationRepositoryPort;
import com.example.creditapplicationservice.domain.port.out.RiskCentralServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfig {
    @Bean
    ApplicationCreateUseCase applicationCreateUseCase(
            ApplicationRepositoryPort applicationRepositoryPort,
            AffiliateRepositoryPort affiliateRepositoryPort,
            RiskCentralServicePort riskCentralServicePort
            ) {
        return new ApplicationService(applicationRepositoryPort, affiliateRepositoryPort, riskCentralServicePort);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
