package com.taller1.microservicios.service.pedido;
import com.taller1.microservicios.dto.pedido.PedidoDto;
import com.taller1.microservicios.dto.pedido.PedidoProductosDto;
import com.taller1.microservicios.dto.pedido.PedidoToSaveDto;
import com.taller1.microservicios.dto.pedido.PedidoToUpdateDto;
import com.taller1.microservicios.exception.ClienteNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.model.enums.EstadoPedido;

import java.util.List;

public interface PedidoService {

    PedidoDto crearPedido(PedidoToSaveDto pedidoToSaveDto) throws ClienteNotFoundException;

    PedidoDto actualizarPedido(Long id, PedidoToUpdateDto pedidoToUpdateDto) throws PedidoNotFoundException;

    PedidoDto buscarPedidoById(Long id) throws PedidoNotFoundException;

    void removerPedido(Long id) throws PedidoNotFoundException;

    List<PedidoDto> getAllPedidos();

    List<PedidoDto> buscarPedidosByRangoFechas(String fechaInicio, String fechaFin);

    List<PedidoDto> buscarPedidoByClienteIdAndEstado(Long clienteId, EstadoPedido estadoPedido);

    List<PedidoProductosDto> buscarPedidosConProductos(Long clienteId);
}
