package com.taller1.microservicios.dto.Pago;

import com.taller1.microservicios.model.enums.MetodoPago;

import java.time.LocalDateTime;

public record PagoToSaveDto(
        Double totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        Long pedidoId
) {
}
