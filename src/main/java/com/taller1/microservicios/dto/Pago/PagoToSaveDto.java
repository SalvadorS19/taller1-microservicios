package com.taller1.microservicios.dto.Pago;

import com.taller1.microservicios.model.enums.MetodoPago;

import java.time.LocalDateTime;

public record PagoToSaveDto(
        MetodoPago metodoPago,
        Long pedidoId
) {
}
