package com.taller1.microservicios.dto.producto;

public record ProductoDto(
        Long id,
        String nombre,
        Double precio,
        Integer stock
) {
}
