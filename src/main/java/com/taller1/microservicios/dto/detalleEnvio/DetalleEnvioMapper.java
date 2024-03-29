package com.taller1.microservicios.dto.detalleEnvio;

import com.taller1.microservicios.model.DetalleEnvio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleEnvioMapper {

    @Mapping(source = "pedido.id", target = "pedidoId")
    DetalleEnvioDto detalleEnvioToDetalleEnvioDto(DetalleEnvio detalleEnvio);

    DetalleEnvio detalleEnvioToSaveDtoToDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    List<DetalleEnvioDto> detalleEnvioListToDetalleEnvioDtoList(List<DetalleEnvio> detalleEnvios);
}
