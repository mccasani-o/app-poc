package com.example.mapper;

import com.example.model.dto.TipoCambioDto;
import com.example.model.entity.TipoCambio;
import org.springframework.beans.BeanUtils;

public class TipoCambioMapper {

    private TipoCambioMapper(){}

    public static TipoCambio mapToTipoCambioEntity(TipoCambioDto tipoCambioDto) {
        TipoCambio tipoCambioEntity =  TipoCambio.builder().build();
        BeanUtils.copyProperties(tipoCambioDto, tipoCambioEntity);
        return tipoCambioEntity;
    }
    public static TipoCambioDto mapToTipoCambioDto(TipoCambio tipoCambioEntity) {
        TipoCambioDto tipoCambioDto =  TipoCambioDto.builder().build();
        BeanUtils.copyProperties(tipoCambioEntity, tipoCambioDto);
        return tipoCambioDto;
    }
}
