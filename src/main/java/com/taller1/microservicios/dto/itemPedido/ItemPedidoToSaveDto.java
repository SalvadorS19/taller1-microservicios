package com.taller1.microservicios.dto.itemPedido;

public record ItemPedidoToSaveDto(
        Integer cantidad,
        Long pedidoId,
        Long productoId
) {
}
