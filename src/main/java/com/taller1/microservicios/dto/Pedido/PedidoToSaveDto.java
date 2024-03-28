package com.taller1.microservicios.dto.Pedido;

import com.taller1.microservicios.model.enums.EstadoPedido;
import com.taller1.microservicios.model.enums.MetodoPago;

import java.time.LocalDateTime;

public record PedidoToSaveDto(
        EstadoPedido estadoPedido,
        Long clienteId,
        Long pagoId
) {
}
