package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.pago.PagoDto;
import com.taller1.microservicios.dto.pago.PagoToSaveDto;
import com.taller1.microservicios.dto.pago.PagoUpdateDto;
import com.taller1.microservicios.service.pago.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    PagoDto crearPago(@RequestBody PagoToSaveDto pagoToSaveDto) {
        return this.pagoService.crearPago(pagoToSaveDto);
    }

    @GetMapping
    List<PagoDto> getAllPagos() {
        return this.pagoService.getAllPagos();
    }

    @GetMapping("/{id}")
    PagoDto getPagoById(@PathVariable Long id) {
        return this.pagoService.buscarPagoById(id);
    }

    @PutMapping("/{id}")
    PagoDto actualizarPago(@PathVariable Long id, @RequestBody PagoUpdateDto pagoUpdateDto) {
        return this.pagoService.actualizarPago(id, pagoUpdateDto);
    }

    @DeleteMapping("/{id}")
    void removerPago(@PathVariable Long id) {
        this.pagoService.removerPago(id);
    }

    @GetMapping("/order/{orderId}")
    PagoDto getPagoByPedidoId(@PathVariable Long orderId) {
        return this.pagoService.buscarPagoByPedidoId(orderId);
    }

    @GetMapping("/date-range")
    List<PagoDto> getPagosByRangoFecha(@RequestParam String startDate, @RequestParam String endDate) {
        return this.pagoService.buscarPagosByRangoFecha(startDate, endDate);
    }
}
