package com.example.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TipoCambioDto {
    private Long id;
    private String monedaOrigen;
    private String monedaDestino;
    private BigDecimal tasa;
}
