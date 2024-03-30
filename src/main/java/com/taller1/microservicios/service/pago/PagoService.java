package com.taller1.microservicios.service.pago;

import com.taller1.microservicios.dto.pago.PagoDto;
import com.taller1.microservicios.dto.pago.PagoToSaveDto;
import com.taller1.microservicios.dto.pago.PagoUpdateDto;
import com.taller1.microservicios.exception.PagoNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;

import java.util.List;

public interface PagoService {
    PagoDto crearPago(PagoToSaveDto pagoToSaveDto) throws RuntimeException;

    PagoDto actualizarPago(Long id, PagoUpdateDto pagoUpdateDto) throws PagoNotFoundException;

    PagoDto buscarPagoById(Long id) throws PagoNotFoundException;

    void removerPago(Long id) throws PagoNotFoundException;

    List<PagoDto> getAllPagos();

    List<PagoDto> buscarPagosByRangoFecha(String fechaInicio, String fechaFin);

    PagoDto buscarPagoByPedidoId(Long pedidoId) throws PagoNotFoundException;
}
