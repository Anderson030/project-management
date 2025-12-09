package com.example.creditapplicationservice.domain.usecase;

import com.example.creditapplicationservice.application.service.ApplicationService;
import com.example.creditapplicationservice.domain.model.Affiliate;
import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;
import com.example.creditapplicationservice.domain.port.out.AffiliateRepositoryPort;
import com.example.creditapplicationservice.domain.port.out.ApplicationRepositoryPort;
import com.example.creditapplicationservice.domain.port.out.RiskCentralServicePort;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.RiskCentralResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enable Mockito
class ApplicationServiceTest {

    @Mock private ApplicationRepositoryPort applicationRepositoryPort;
    @Mock private AffiliateRepositoryPort affiliateRepositoryPort;
    @Mock private RiskCentralServicePort riskCentralServicePort;

    @InjectMocks
    private ApplicationService applicationService;

    private Affiliate affiliate;
    private CreditApplication application;

    @BeforeEach
    void setUp() {
        // Base data for tests
        affiliate = new Affiliate(1L, "102030", "Pepito", new BigDecimal("5000000"), LocalDate.now().minusMonths(12), true);

        application = new CreditApplication();
        application.setAffiliateId(1L);
        application.setAmount(new BigDecimal("1000000"));
        application.setTerm(12);
        application.setProposalRate(new BigDecimal("1.5"));
    }

    @Test
    void create_ShouldApprove_WhenRulesAreMet() {
        // 1. Arrange (Prepare mocks)
        when(affiliateRepositoryPort.findById(1L)).thenReturn(affiliate);

        // Simulate positive response from risk service
        RiskCentralResponse riskResponse = new RiskCentralResponse("102030", 800, "BAJO", "Todo OK");
        when(riskCentralServicePort.evaluateRisk(any(), any(), any())).thenReturn(riskResponse);

        // Simulate save
        when(applicationRepositoryPort.save(any(CreditApplication.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act (Execute)
        CreditApplication result = applicationService.create(application);

        // 3. Assert (Verify)
        assertEquals(ApplicationStatus.APROBADO, result.getStatus());
        assertNotNull(result.getEvaluation());
        assertEquals(800, result.getEvaluation().getScore());
    }

    @Test
    void create_ShouldReject_WhenRiskIsHigh() {
        // 1. Arrange
        when(affiliateRepositoryPort.findById(1L)).thenReturn(affiliate);

        // Simulate HIGH RISK
        RiskCentralResponse riskResponse = new RiskCentralResponse("102030", 300, "ALTO", "Peligro");
        when(riskCentralServicePort.evaluateRisk(any(), any(), any())).thenReturn(riskResponse);

        when(applicationRepositoryPort.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act
        CreditApplication result = applicationService.create(application);

        // 3. Assert
        assertEquals(ApplicationStatus.RECHAZADO, result.getStatus());
        assertTrue(result.getEvaluation().getReason().contains("RECHAZADO"));
    }
}