package com.example.creditapplicationservice.infrastructure.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskCentralResponse {
    private String documento;
    private Integer score;
    private String nivelRiesgo;
    private String detalle;
}