package com.taller1.microservicios.dto.itemPedido;

public record ItemPedidoDto(
        Long id,
        Integer cantidad,
        Double precioUnitario,
        Long pedidoId,
        Long productoId
) {
}
