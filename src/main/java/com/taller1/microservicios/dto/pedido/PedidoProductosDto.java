package com.taller1.microservicios.dto.pedido;

import com.taller1.microservicios.dto.itemPedido.ItemPedidoDto;
import com.taller1.microservicios.model.enums.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoProductosDto(
        Long id,
        LocalDateTime fechaPedido,
        EstadoPedido estadoPedido,
        Long clienteId,
        Long pagoId,
        Long detalleEnvioId,
        List<ItemPedidoDto> itemsPedido
) {
}
