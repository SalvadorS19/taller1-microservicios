package com.taller1.microservicios.dto.pago;

import com.taller1.microservicios.model.enums.MetodoPago;
import java.time.LocalDateTime;

public record PagoDto(
        Long id,
        Double totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        Long pedidoId
) {
}
