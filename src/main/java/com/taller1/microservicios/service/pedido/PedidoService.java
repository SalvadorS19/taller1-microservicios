package com.taller1.microservicios.service.pedido;
import com.taller1.microservicios.dto.Pedido.PedidoDto;
import com.taller1.microservicios.dto.Pedido.PedidoToSaveDto;
import com.taller1.microservicios.model.enums.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {

    PedidoDto crearPedido(PedidoToSaveDto pedidoToSaveDto);

    PedidoDto actualizarPedido(Long id, PedidoToSaveDto pedidoToSaveDto);

    PedidoDto buscarPedidoById(Long id);

    void removerPedido(Long id);

    List<PedidoDto> getAllPedidos();

    List<PedidoDto> buscarPedidosByRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<PedidoDto> buscarPedidoByClienteIdAndEstado(Long clienteId, EstadoPedido estadoPedido);

    List<?> buscarPedidosConProductos(Long clienteId);
}