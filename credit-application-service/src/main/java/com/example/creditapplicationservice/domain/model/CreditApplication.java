package com.example.creditapplicationservice.domain.model;

import com.example.creditapplicationservice.domain.model.enums.ApplicationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditApplication {
    private Long id;
    private Long affiliateId;
    private BigDecimal amount;
    private int term;
    private BigDecimal proposalRate;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private Evaluation evaluation;

    public CreditApplication() {}

    public CreditApplication(Long id, Long affiliateId, BigDecimal amount, int term, BigDecimal proposalRate,
                             LocalDate applicationDate, ApplicationStatus status, Evaluation evaluation){
        this.id = id;
        this.affiliateId = affiliateId;
        this.amount = amount;
        this.term = term;
        this.proposalRate = proposalRate;
        this.applicationDate = applicationDate;
        this.status = status;
        this.evaluation = evaluation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAffiliateId() { return affiliateId; }
    public void setAffiliateId(Long affiliateId) { this.affiliateId = affiliateId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public int getTerm() { return term; }
    public void setTerm(int term) { this.term = term; }

    public BigDecimal getProposalRate() { return proposalRate; }
    public void setProposalRate(BigDecimal proposalRate) { this.proposalRate = proposalRate; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public Evaluation getEvaluation() { return evaluation; }

    public void setEvaluation(Evaluation evaluation) { this.evaluation = evaluation; }
}
