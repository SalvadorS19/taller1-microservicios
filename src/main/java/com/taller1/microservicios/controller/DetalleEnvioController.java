package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioUpdateDto;
import com.taller1.microservicios.model.enums.EstadoEnvio;
import com.taller1.microservicios.service.detalleEnvio.DetalleEnvioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class DetalleEnvioController {

    private final DetalleEnvioService detalleEnvioService;

    public DetalleEnvioController(DetalleEnvioService detalleEnvioService) {
        this.detalleEnvioService = detalleEnvioService;
    }

    @PostMapping
    DetalleEnvioDto crearDetalleEnvio(@RequestBody DetalleEnvioToSaveDto detalleEnvioToSaveDto){
        return this.detalleEnvioService.crearDetalleEnvio(detalleEnvioToSaveDto);
    }

    @PutMapping("/{id}")
    DetalleEnvioDto actualizarDetalleEnvio(@PathVariable Long id,@RequestBody DetalleEnvioUpdateDto detalleEnvioUpdateDto){
        return  this.detalleEnvioService.actualizarDetalleEnvio(id,detalleEnvioUpdateDto);
    }

    @GetMapping("/{id}")
    DetalleEnvioDto buscarDetalleEnvioById(@PathVariable Long id){
        return this.detalleEnvioService.buscarDetalleEnvioById(id);
    }

    @DeleteMapping("/{id}")
    void removerDetalleEnvio(@PathVariable Long id){
        this.detalleEnvioService.removerDetalleEnvio(id);
    }

    @GetMapping
    List<DetalleEnvioDto> getAllDetalleEnvio(){
        return this.detalleEnvioService.getAllDetalleEnvio();
    }

    @GetMapping("/order/{orderId}")
    DetalleEnvioDto getDetalleEnvioByPedidoId(@PathVariable Long orderId){
        return this.detalleEnvioService.getDetalleEnvioByPedidoId(orderId);
    }

    @GetMapping("/carrier")
    List<DetalleEnvioDto> getDetalleEnviosByTransportadora(@RequestParam String carrier){
        return this.detalleEnvioService.getDetalleEnviosByTransportadora(carrier);
    }

    @GetMapping("/estadoEnvio")
    List<DetalleEnvioDto> getDetalleEnviosByEstado(@RequestParam EstadoEnvio estadoEnvio){
        return this.detalleEnvioService.getDetalleEnviosByEstado(estadoEnvio);
    }
}
