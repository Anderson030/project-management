package com.example.creditapplicationservice.infrastructure.adapter.in.dto;

import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationResponse {
    private Long id;
    private Long affiliateId;
    private BigDecimal amount;
    private int term;
    private BigDecimal proposalRate;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private EvaluationResponse evaluation;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvaluationResponse {
        private Integer score;
        private String riskLevel;
        private String reason;
        private LocalDateTime evaluationDate;
    }
}
