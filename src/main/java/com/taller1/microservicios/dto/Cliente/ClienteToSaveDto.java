package com.taller1.microservicios.dto.Cliente;

public record ClienteToSaveDto(
        String nombre,
        String email,
        String direccion
) {
}
