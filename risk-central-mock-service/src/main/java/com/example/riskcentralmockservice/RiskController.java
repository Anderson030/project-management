package com.example.riskcentralmockservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/risk-evaluation")
public class RiskController {
    @PostMapping
    public ResponseEntity<RiskResponse> evaluateRisk(@RequestBody RiskRequest request) {
        // Score from the document:

        String document = request.getDocumento();

        int seed = Math.abs(document.hashCode() % 1000);
        int score = 300 + (int) (seed / 999.0 * (950 - 300));

        String nivelRiesgo;
        String detalle;

        if (score > 700) {
            nivelRiesgo = "BAJO";
            detalle = "El cliente presenta un bajo riesgo crediticio.";
        } else if (score > 500) {
            nivelRiesgo = "MEDIO";
            detalle = "El cliente presenta un riesgo crediticio moderado.";
        } else {
            nivelRiesgo = "ALTO";
            detalle = "El cliente presenta un alto riesgo crediticio.";
        }

        RiskResponse response = new RiskResponse(request.getDocumento(), score, nivelRiesgo, detalle);
        return ResponseEntity.ok(response);
    }

}
