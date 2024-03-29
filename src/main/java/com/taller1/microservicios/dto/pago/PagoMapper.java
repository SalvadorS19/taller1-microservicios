package com.taller1.microservicios.dto.pago;

import com.taller1.microservicios.model.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface PagoMapper {
    @Mapping(source = "pedido.id", target = "pedidoId")
    PagoDto pagoToPagoDto(Pago pago);
    @Mapping(target = "fechaPago", expression = "java(java.time.LocalDateTime.now())")
    Pago pagoToSaveDtoToPago(PagoToSaveDto pagoToSaveDto);
    List<PagoDto> pagoListToPagoDtoList(List<Pago> pagos);
}
