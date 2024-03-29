package com.taller1.microservicios.dto.detalleEnvio;

public record DetalleEnvioToSaveDto(
        String direccion,
        String transportadora,
        Long pedidoId
) {
}
