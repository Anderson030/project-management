package com.example.creditapplicationservice.domain.model;

import com.example.creditapplicationservice.domain.model.enums.RiskLevel;

import java.time.LocalDateTime;

public class Evaluation {
    private Long id;
    private Long applicationId;
    private Integer score;
    private RiskLevel riskLevel;
    private String reason;
    private LocalDateTime evaluationDate;

    public Evaluation() {}

    public Evaluation(Long id, Long applicationId, Integer score, RiskLevel riskLevel, String reason, LocalDateTime evaluationDate) {
        this.id = id;
        this.applicationId = applicationId;
        this.score = score;
        this.riskLevel = riskLevel;
        this.reason = reason;
        this.evaluationDate = evaluationDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getEvaluationDate() { return evaluationDate; }
    public void setEvaluationDate(LocalDateTime evaluationDate) { this.evaluationDate = evaluationDate; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
}
