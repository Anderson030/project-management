package com.example.riskcentralmockservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskResponse {
    private String documento;
    private int score;
    private String nivelRiesgo;
    private String detalle;
}
