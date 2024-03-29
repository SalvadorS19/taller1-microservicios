package com.taller1.microservicios.service.pedido;
import com.taller1.microservicios.dto.Pedido.PedidoDto;
import com.taller1.microservicios.dto.Pedido.PedidoProductosDto;
import com.taller1.microservicios.dto.Pedido.PedidoToSaveDto;
import com.taller1.microservicios.dto.Pedido.PedidoToUpdateDto;
import com.taller1.microservicios.model.enums.EstadoPedido;

import java.util.List;

public interface PedidoService {

    PedidoDto crearPedido(PedidoToSaveDto pedidoToSaveDto);

    PedidoDto actualizarPedido(Long id, PedidoToUpdateDto pedidoToUpdateDto);

    PedidoDto buscarPedidoById(Long id);

    void removerPedido(Long id);

    List<PedidoDto> getAllPedidos();

    List<PedidoDto> buscarPedidosByRangoFechas(String fechaInicio, String fechaFin);

    List<PedidoDto> buscarPedidoByClienteIdAndEstado(Long clienteId, EstadoPedido estadoPedido);

    List<PedidoProductosDto> buscarPedidosConProductos(Long clienteId);
}
