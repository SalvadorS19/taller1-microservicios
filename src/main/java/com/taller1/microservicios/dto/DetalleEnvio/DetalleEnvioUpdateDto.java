package com.taller1.microservicios.dto.DetalleEnvio;

import com.taller1.microservicios.model.enums.EstadoEnvio;

public record DetalleEnvioUpdateDto(
        String direccion,
        String transportadora,
        EstadoEnvio estadoEnvio
) {
}
