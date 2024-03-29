package com.taller1.microservicios.dto.cliente;

public record ClienteToSaveDto(
        String nombre,
        String email,
        String direccion
) {
}
