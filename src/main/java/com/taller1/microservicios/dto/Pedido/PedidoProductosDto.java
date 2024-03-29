package com.taller1.microservicios.dto.Pedido;

import com.taller1.microservicios.dto.ItemPedido.ItemPedidoDto;
import com.taller1.microservicios.model.enums.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoProductosDto(
        Long id,
        LocalDateTime fechaPedido,
        EstadoPedido estadoPedido,
        Long clienteId,
        List<ItemPedidoDto> itemPedidos
) {
}
