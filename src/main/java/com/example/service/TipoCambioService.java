package com.example.service;

import com.example.model.dto.TipoCambioDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface TipoCambioService {
    Mono<BigDecimal> convertir(BigDecimal cantidad, String monedaOrigen, String monedaDestino);

    Mono<Void> insertar(TipoCambioDto tipoCambioDto);

    Mono<Void> actualizar(Long id, TipoCambioDto tipoCambioDto);

    Flux<TipoCambioDto> listarTipoCambio();

    Mono<TipoCambioDto> buscarXid(Long id);
}
