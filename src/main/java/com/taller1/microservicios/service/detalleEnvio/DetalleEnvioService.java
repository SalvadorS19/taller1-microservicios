package com.taller1.microservicios.service.detalleEnvio;

import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioUpdateDto;
import com.taller1.microservicios.model.DetalleEnvio;
import com.taller1.microservicios.model.enums.EstadoEnvio;

import java.util.List;

public interface DetalleEnvioService {
    DetalleEnvioDto crearDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    DetalleEnvioDto actualizarDetalleEnvio(Long id, DetalleEnvioUpdateDto detalleEnvioUpdateDto);

    DetalleEnvioDto buscarDetalleEnvioById(Long id);

    void removerDetalleEnvio(Long id);

    List<DetalleEnvioDto> getAllDetalleEnvio();

    DetalleEnvioDto getDetalleEnvioByPedidoId(Long id);

    List<DetalleEnvioDto> getDetalleEnviosByTransportadora(String transportadora);

    List<DetalleEnvioDto> getDetalleEnviosByEstado(EstadoEnvio estadoEnvio);
}
