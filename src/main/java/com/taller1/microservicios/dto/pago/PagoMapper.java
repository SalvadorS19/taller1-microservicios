package com.taller1.microservicios.dto.pago;

import com.taller1.microservicios.model.Pago;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface PagoMapper {
    PagoDto pagoToPagoDto(Pago pago);
    Pago pagoToSaveDtoToPago(PagoToSaveDto pagoToSaveDto);
    List<PagoDto> pagoListToPagoDtoList(List<Pago> pago);
}
