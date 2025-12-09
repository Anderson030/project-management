package com.example.riskcentralmockservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskRequest {
    private String documento;
    private double monto;
    private int plazo;
}
