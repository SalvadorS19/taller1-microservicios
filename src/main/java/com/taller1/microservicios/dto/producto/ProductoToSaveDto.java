package com.taller1.microservicios.dto.producto;

public record ProductoToSaveDto(

        String nombre,
        Double precio,
        Integer stock

) {
}
