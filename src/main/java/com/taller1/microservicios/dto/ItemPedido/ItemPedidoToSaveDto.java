package com.taller1.microservicios.dto.ItemPedido;

public record ItemPedidoToSaveDto(
        Integer cantidad,
        Double precioUnitario,
        Long pedidoId,
        Long productoId
) {
}
