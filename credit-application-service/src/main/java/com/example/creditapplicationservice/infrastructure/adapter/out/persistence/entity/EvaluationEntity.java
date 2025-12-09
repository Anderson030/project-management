package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity;

import com.example.creditapplicationservice.domain.model.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RiskLevel riskLevel;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;

    @OneToOne
    @JoinColumn(name = "application_id")
    //@ToString.Exclude
    //@EqualsAndHashCode.Exclude
    private CreditApplicationEntity application;
}