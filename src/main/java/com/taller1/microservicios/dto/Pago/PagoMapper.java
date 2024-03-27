package com.taller1.microservicios.dto.Pago;

import com.taller1.microservicios.dto.Pago.PagoDto;
import com.taller1.microservicios.dto.Pago.PagoToSaveDto;
import com.taller1.microservicios.model.Pago;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface PagoMapper {
    PagoDto pagoToPagoDto(Pago pago);
    Pago pagoDtoToPago(PagoDto pagoDto);
    Pago pagoToSaveDtoToPago(PagoToSaveDto pagoToSaveDto);
}
