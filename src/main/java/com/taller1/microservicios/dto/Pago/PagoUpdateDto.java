package com.taller1.microservicios.dto.Pago;

import com.taller1.microservicios.model.enums.MetodoPago;

public record PagoUpdateDto(
        MetodoPago metodoPago
) {
}
