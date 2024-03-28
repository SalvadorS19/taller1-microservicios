package com.taller1.microservicios.dto.ItemPedido;

public record ItemPedidoToSaveDto(
        Integer cantidad,
        Long pedidoId,
        Long productoId
) {
}
