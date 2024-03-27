package com.taller1.microservicios.dto.Producto;

public record ProductoToSaveDto(

        String nombre,
        Double precio,
        Integer stock

) {
}
