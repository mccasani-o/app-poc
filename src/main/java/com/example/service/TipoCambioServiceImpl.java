package com.example.service;

import com.example.mapper.TipoCambioMapper;
import com.example.model.dto.TipoCambioDto;
import com.example.model.entity.TipoCambio;
import com.example.repository.TipoCambioRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class TipoCambioServiceImpl implements TipoCambioService {

    private final TipoCambioRepository tipoCambioRepository;

    public TipoCambioServiceImpl(TipoCambioRepository tipoCambioRepository) {
        this.tipoCambioRepository = tipoCambioRepository;
    }

    @Override
    public Mono<BigDecimal> convertir(BigDecimal cantidad, String monedaOrigen, String monedaDestino) {
        return tipoCambioRepository.findByMonedaOrigenAndMonedaDestino(monedaOrigen, monedaDestino)
                .switchIfEmpty(Mono.error(new RuntimeException("Tipo de cambio no encontrado")))
                .map(tipoCambio -> cantidad.multiply(tipoCambio.getTasa()));
    }

    @Override
    public Mono<Void> insertar(TipoCambioDto tipoCambioDto) {
        return tipoCambioRepository.save(TipoCambioMapper.mapToTipoCambioEntity(tipoCambioDto))
                .then();
    }


    @Override
    public Mono<Void> actualizar(Long id, TipoCambioDto tipoCambioDto) {
        return this.buscarTipoCambioXid(id)
                .flatMap(tipoCambio -> {
                    tipoCambio.setTasa(tipoCambioDto.getTasa());
                    tipoCambio.setMonedaOrigen(tipoCambioDto.getMonedaOrigen());
                    tipoCambio.setMonedaDestino(tipoCambioDto.getMonedaDestino());
                    return tipoCambioRepository.save(tipoCambio);
                })
                .then();
    }

    @Override
    public Flux<TipoCambioDto> listarTipoCambio() {
        return this.tipoCambioRepository.findAll()
                .map(TipoCambioMapper::mapToTipoCambioDto);
    }

    @Override
    public Mono<TipoCambioDto> buscarXid(Long id) {
        return this.buscarTipoCambioXid(id)
                .map(this::convertirADto);
    }


    private Mono<TipoCambio> buscarTipoCambioXid(Long id) {
        return this.tipoCambioRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Tipo de cambio no encontrado por id")));
    }


    private TipoCambioDto convertirADto(TipoCambio tipoCambio) {

        return TipoCambioMapper.mapToTipoCambioDto(tipoCambio);
    }
}
