package com.example.creditapplicationservice.application.service;

import com.example.creditapplicationservice.domain.model.Affiliate;
import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.model.Evaluation;
import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;
import com.example.creditapplicationservice.domain.model.enums.RiskLevel;
import com.example.creditapplicationservice.domain.port.in.ApplicationCreateUseCase;
import com.example.creditapplicationservice.domain.port.out.AffiliateRepositoryPort;
import com.example.creditapplicationservice.domain.port.out.ApplicationRepositoryPort;
import com.example.creditapplicationservice.domain.port.out.RiskCentralServicePort;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.RiskCentralResponse;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Service to handle credit application creation and evaluation.
 */
@Transactional
public class ApplicationService implements ApplicationCreateUseCase {

    private final ApplicationRepositoryPort applicationRepositoryPort;
    private final AffiliateRepositoryPort affiliateRepositoryPort;
    private final RiskCentralServicePort riskCentralServicePort;

    public ApplicationService(
            ApplicationRepositoryPort applicationRepositoryPort,
                              AffiliateRepositoryPort affiliateRepositoryPort,
                              RiskCentralServicePort evaluationRepositoryPort
    ) {
        this.applicationRepositoryPort = applicationRepositoryPort;
        this.affiliateRepositoryPort = affiliateRepositoryPort;
        this.riskCentralServicePort = evaluationRepositoryPort;
    }

    @Override
    public CreditApplication create(CreditApplication application) {
        application.setApplicationDate(LocalDate.now());
        application.setStatus(ApplicationStatus.PENDIENTE);

        Affiliate affiliate = affiliateRepositoryPort.findById(application.getAffiliateId());

        RiskCentralResponse apiResponse = riskCentralServicePort.evaluateRisk(
                affiliate.getDocument(),
                application.getAmount(),
                application.getTerm()
        );

        Integer score = apiResponse.getScore();
        RiskLevel riskLevel = mapRiskLevel(apiResponse.getNivelRiesgo()); // Método auxiliar para convertir String a Enum
        String detalle = apiResponse.getDetalle();

        Evaluation evaluacion = excuteBussinesRules(application, affiliate, score, riskLevel);
        application.setEvaluation(evaluacion);

        if (evaluacion.getReason().contains("APROBADA")) {
            application.setStatus(ApplicationStatus.APROBADO);
        } else {
            application.setStatus(ApplicationStatus.RECHAZADO);
        }

        return applicationRepositoryPort.save(application);
    }

    /**
     * Executes a set of business rules to determine application approval.
     */
    private Evaluation excuteBussinesRules(CreditApplication solicitud, Affiliate affiliate, Integer score, RiskLevel riskLevel) {
        StringBuilder reasons = new StringBuilder();
        boolean approbed = true;

        // Rule A: Check affiliate's seniority (must be >= 6 months)
        long mesesAntiguedad = ChronoUnit.MONTHS.between(affiliate.getAffiliationDate(), LocalDate.now());
        if (mesesAntiguedad < 6) {
            approbed = false;
            reasons.append("Antigüedad insuficiente (").append(mesesAntiguedad).append(" meses). ");
        }

        // Rule B: Check credit score and risk level
        if (score < 500 || riskLevel == RiskLevel.ALTO) {
            approbed = false;
            reasons.append("Puntaje crediticio insuficiente o Riesgo Alto. ");
        }

        // Rule C: Check payment capacity (monthly payment <= 30% of salary)
        BigDecimal monthlyPay = calculateRate(solicitud.getAmount(), solicitud.getTerm(), solicitud.getProposalRate());
        BigDecimal limiteCuota = affiliate.getSalary().multiply(new BigDecimal("0.30"));

        if (monthlyPay.compareTo(limiteCuota) > 0) {
            approbed = false;
            reasons.append("Capacidad de pago insuficiente. Cuota estimada: ").append(monthlyPay).append(". ");
        }

        // Build the final decision reason
        String reason = approbed ? "Solicitud APROBADA satisfactoriamente." : "RECHAZADO: " + reasons.toString();

        return new Evaluation(
                null,
                null,
                score,
                riskLevel,
                reason,
                LocalDateTime.now()
        );
    }

    /**
     * Calculates the monthly installment using the French amortization formula.
     */
    private BigDecimal calculateRate(BigDecimal monto, int plazo, BigDecimal tasaMensualPorcentaje) {
        // Convert percentage rate to decimal
        BigDecimal tasaDecimal = tasaMensualPorcentaje.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);

        double p = monto.doubleValue(); // Principal
        double i = tasaDecimal.doubleValue(); // Interest rate
        double n = plazo; // Number of periods

        // French amortization formula: C = (P * i) / (1 - (1 + i)^-n)
        double cuota = (p * i) / (1 - Math.pow(1 + i, -n));

        return BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP);
    }

    private RiskLevel mapRiskLevel(String riskLevelString) {
        if (riskLevelString == null) {
            return null; // Or throw an exception, or return a default value
        }
        return switch (riskLevelString.toUpperCase()) {
            case "BAJO" -> RiskLevel.BAJO;
            case "MEDIO" -> RiskLevel.MEDIO;
            case "ALTO" -> RiskLevel.ALTO;
            default -> throw new IllegalArgumentException("Unknown risk level: " + riskLevelString);
        };
    }
}
