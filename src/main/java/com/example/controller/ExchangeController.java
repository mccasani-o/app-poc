package com.example.controller;

import com.example.model.dto.ExchangeDto;
import com.example.model.dto.TipoCambioDto;
import com.example.service.TipoCambioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final TipoCambioService tipoCambioService;

    public ExchangeController(TipoCambioService tipoCambioService) {
        this.tipoCambioService = tipoCambioService;
    }

    @PostMapping("/convertir")
    public Mono<ResponseEntity<BigDecimal>> convertirMoneda(@RequestBody ExchangeDto request) {
        return tipoCambioService.convertir(request.getCantidad(), request.getMonedaOrigen(), request.getMonedaDestino())
                .map(ResponseEntity::ok);
    }


    @GetMapping
    public ResponseEntity<Flux<TipoCambioDto>> listar() {
        return ResponseEntity.ok(this.tipoCambioService.listarTipoCambio());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> crear(@RequestBody TipoCambioDto tipoCambioDto) {
       return this.tipoCambioService.insertar(tipoCambioDto);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> update(@PathVariable Long id,@RequestBody TipoCambioDto tipoCambioDto) {
       return this.tipoCambioService.actualizar(id,tipoCambioDto);
    }

    @GetMapping("/{id}")
    public Mono<TipoCambioDto> update(@PathVariable Long id) {
        return this.tipoCambioService.buscarXid(id);
    }

}
