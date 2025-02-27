package com.example.repository;

import com.example.model.entity.TipoCambio;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface TipoCambioRepository extends ReactiveCrudRepository<TipoCambio, Long> {
    Mono<TipoCambio> findByMonedaOrigenAndMonedaDestino(String source, String target);
}
