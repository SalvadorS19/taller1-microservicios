package com.taller1.microservicios.service.detalleEnvio;

import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioUpdateDto;
import com.taller1.microservicios.exception.DetalleEnvioNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.model.enums.EstadoEnvio;

import java.util.List;

public interface DetalleEnvioService {
    DetalleEnvioDto crearDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto) throws PedidoNotFoundException;

    DetalleEnvioDto actualizarDetalleEnvio(Long id, DetalleEnvioUpdateDto detalleEnvioUpdateDto) throws DetalleEnvioNotFoundException;

    DetalleEnvioDto buscarDetalleEnvioById(Long id) throws DetalleEnvioNotFoundException;

    void removerDetalleEnvio(Long id) throws DetalleEnvioNotFoundException;

    List<DetalleEnvioDto> getAllDetalleEnvio();

    DetalleEnvioDto getDetalleEnvioByPedidoId(Long id) throws DetalleEnvioNotFoundException;

    List<DetalleEnvioDto> getDetalleEnviosByTransportadora(String transportadora);

    List<DetalleEnvioDto> getDetalleEnviosByEstado(EstadoEnvio estadoEnvio);
}
