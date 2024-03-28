package com.taller1.microservicios.dto.ItemPedido;

public record ItemPedidoUpdateDto(
        Integer cantidad,
        Double precioUnitario
) {
}
