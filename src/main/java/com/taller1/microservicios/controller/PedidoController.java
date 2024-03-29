package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.pedido.PedidoDto;
import com.taller1.microservicios.dto.pedido.PedidoProductosDto;
import com.taller1.microservicios.dto.pedido.PedidoToSaveDto;
import com.taller1.microservicios.dto.pedido.PedidoToUpdateDto;
import com.taller1.microservicios.model.enums.EstadoPedido;
import com.taller1.microservicios.service.pedido.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    PedidoDto crearPedido(@RequestBody PedidoToSaveDto pedidoToSaveDto) {
        return this.pedidoService.crearPedido(pedidoToSaveDto);
    }

    @GetMapping
    List<PedidoDto> getAllPedidos() {
        return this.pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    PedidoDto getPedidoById(@PathVariable Long id) {
        return this.pedidoService.buscarPedidoById(id);
    }

    @PutMapping("/{id}")
    PedidoDto actualizarPedido(@PathVariable Long id, @RequestBody PedidoToUpdateDto pedidoToUpdateDto) {
        return this.pedidoService.actualizarPedido(id, pedidoToUpdateDto);
    }

    @DeleteMapping("/{id}")
    void eliminarPedido(@PathVariable Long id) {
        this.pedidoService.removerPedido(id);
    }

    @GetMapping("/date-range")
    List<PedidoDto> filtrarPorRangoFechas(@RequestParam String startDate, @RequestParam String endDate) {
        return this.pedidoService.buscarPedidosByRangoFechas(startDate, endDate);
    }

    @GetMapping("/filter")
    List<PedidoDto> pedidosPorFiltro(@RequestParam Long clienteId, @RequestParam EstadoPedido estadoPedido) {
        return this.pedidoService.buscarPedidoByClienteIdAndEstado(clienteId, estadoPedido);
    }

    @GetMapping("/customer/{customerId}")
    List<PedidoProductosDto> getPedidosConProductosByClienteId(@PathVariable Long customerId) {
        return this.pedidoService.buscarPedidosConProductos(customerId);
    }
}
