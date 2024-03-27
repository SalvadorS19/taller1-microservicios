package com.taller1.microservicios.dto.DetalleEnvio;

import com.taller1.microservicios.model.enums.EstadoEnvio;

public record DetalleEnvioDto(
        Long id,
        String direccion,
        String transportadora,
        EstadoEnvio estadoEnvio,
        String numeroGuia,
        Long pedidoId
) {
}
