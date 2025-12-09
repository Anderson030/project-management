package com.example.creditapplicationservice.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationRequest {
    @NotNull(message = "El ID del afiliado no puede estar vacío.")
    private Long affiliateId;

    @NotNull(message = "La cantidad no puede estar vacía.")
    @Positive(message = "La cantidad a solicitar debe ser positiva.")
    private BigDecimal amount;

    @NotNull(message = "Debe ingresar un plazo.")
    @Positive(message = "El plazo debe ser positivo.")
    private Integer term;

    @NotNull(message = "La tasa propuesta es obligatoria")
    private BigDecimal proposalRate;
}
