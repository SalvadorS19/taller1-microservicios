package com.taller1.microservicios.dto.pedido;

import com.taller1.microservicios.model.enums.EstadoPedido;

import java.time.LocalDateTime;

public record PedidoDto(
        Long id,
        LocalDateTime fechaPedido,
        EstadoPedido estadoPedido,
        Long clienteId,
        Long pagoId,
        Long detalleEnvioId
) {
}
