package com.taller1.microservicios.dto.Cliente;

public record ClienteDto(
        Long id,
        String nombre,
        String email,
        String direccion
) {
}
