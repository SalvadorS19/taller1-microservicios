package com.taller1.microservicios.dto.ItemPedido;

public record ItemPedidoDto(
        Long id,
        Integer cantidad,
        Double precioUnitario,
        Long pedidoId,
        Long productoId
) {
}
