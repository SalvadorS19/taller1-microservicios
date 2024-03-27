package com.taller1.microservicios.dto.DetalleEnvio;

import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.model.DetalleEnvio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface DetalleEnvioMapper {
    DetalleEnvioDto detalleEnvioToDetalleEnvioDto(DetalleEnvio detalleEnvio);
    DetalleEnvio detalleEnvioDtoToDetalleEnvio(DetalleEnvioDto detalleEnvioDto);
    DetalleEnvio detalleEnvioToSaveDtoToDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);
}
