package com.example.model.dto;

import lombok.Data;

import java.math.BigDecimal;

//Intercambio dto
@Data
public class ExchangeDto {
    private BigDecimal cantidad;
    private String monedaOrigen;
    private String monedaDestino;
}
