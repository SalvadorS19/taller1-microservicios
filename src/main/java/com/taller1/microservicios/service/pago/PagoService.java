package com.taller1.microservicios.service.pago;

import com.taller1.microservicios.dto.pago.PagoDto;
import com.taller1.microservicios.dto.pago.PagoToSaveDto;
import com.taller1.microservicios.dto.pago.PagoUpdateDto;

import java.util.List;

public interface PagoService {
    PagoDto crearPago(PagoToSaveDto pagoToSaveDto);

    PagoDto actualizarPago(Long id, PagoUpdateDto pagoUpdateDto);

    PagoDto buscarPagoById(Long id);

    void removerPago(Long id);

    List<PagoDto> getAllPagos();

    List<PagoDto> buscarPagosByRangoFecha(String fechaInicio, String fechaFin);

    PagoDto buscarPagoByPedidoId(Long pedidoId);
}
