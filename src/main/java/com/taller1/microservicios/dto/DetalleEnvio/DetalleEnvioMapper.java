package com.taller1.microservicios.dto.DetalleEnvio;

import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.model.DetalleEnvio;
import com.taller1.microservicios.service.pedido.PedidoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PedidoService.class})
public interface DetalleEnvioMapper {

    @Mapping(source = "pedido.id", target = "pedidoId")
    DetalleEnvioDto detalleEnvioToDetalleEnvioDto(DetalleEnvio detalleEnvio);

    DetalleEnvio detalleEnvioToSaveDtoToDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    List<DetalleEnvioDto> detalleEnvioListToDetalleEnvioDtoList(List<DetalleEnvio> detalleEnvios);
}
