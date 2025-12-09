package com.example.creditapplicationservice.infrastructure.adapter.out.external;

import com.example.creditapplicationservice.domain.port.out.RiskCentralServicePort;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.RiskCentralResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HttpRiskCentralServiceAdapter implements RiskCentralServicePort {

    private final RestTemplate restTemplate;

    private final String API_URL = "http://localhost:8082/risk-evaluation";

    @Override
    public RiskCentralResponse evaluateRisk(String document, BigDecimal amount, Integer term) {

        RiskCentralRequest request = new RiskCentralRequest(
                document,
                amount,
                term
        );

        try {
            RiskCentralResponse response = restTemplate.postForObject(
                    API_URL,
                    request,
                    RiskCentralResponse.class
            );

            if (response == null) {
                throw new RuntimeException("El servicio de riesgo no retornó datos.");
            }

            return response;

        } catch (Exception e) {
            System.err.println("Error consultando la Central de Riesgos: " + e.getMessage());
            throw new RuntimeException("No se pudo evaluar el riesgo del cliente. Servicio no disponible.");
        }
    }

    @Data
    @AllArgsConstructor
    private static class RiskCentralRequest {
        private String documento;
        private BigDecimal monto;
        private Integer plazo;
    }
}
