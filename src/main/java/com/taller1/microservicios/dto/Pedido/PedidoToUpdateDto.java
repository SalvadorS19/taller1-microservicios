package com.taller1.microservicios.dto.Pedido;

import com.taller1.microservicios.model.enums.EstadoPedido;

public record PedidoToUpdateDto(
        EstadoPedido estadoPedido
) {
}
