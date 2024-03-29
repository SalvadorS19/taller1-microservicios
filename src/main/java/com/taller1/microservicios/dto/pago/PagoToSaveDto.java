package com.taller1.microservicios.dto.pago;

import com.taller1.microservicios.model.enums.MetodoPago;

public record PagoToSaveDto(
        MetodoPago metodoPago,
        Long pedidoId
) {
}
