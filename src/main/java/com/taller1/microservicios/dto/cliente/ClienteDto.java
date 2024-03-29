package com.taller1.microservicios.dto.cliente;

public record ClienteDto(
        Long id,
        String nombre,
        String email,
        String direccion
) {
}
