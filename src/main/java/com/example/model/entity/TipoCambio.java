package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class TipoCambio {
    @Id
    private Long id;
    @Column("moneda_origen")
    private String monedaOrigen;
    @Column("moneda_destino")
    private String monedaDestino;
    private BigDecimal tasa;
}

