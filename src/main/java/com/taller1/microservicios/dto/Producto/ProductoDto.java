package com.taller1.microservicios.dto.Producto;

public record ProductoDto(
        Long id,
        String nombre,
        Double precio,
        Integer stock
) {
}
