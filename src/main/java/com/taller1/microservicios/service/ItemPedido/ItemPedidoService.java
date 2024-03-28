package com.taller1.microservicios.service.ItemPedido;

import com.taller1.microservicios.dto.ItemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoUpdateDto;

import java.util.List;

public interface ItemPedidoService {
    ItemPedidoDto crearItemPedido (ItemPedidoToSaveDto itemPedidoToSaveDto);
    ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoUpdateDto itemPedidoUpdateDto);
    ItemPedidoDto buscarItemPedidoById(Long id);
    void removerItemPedido(Long id);
    List<ItemPedidoDto> getAllItemPedidos();
    List<ItemPedidoDto> buscarItemPedidoByidPedido(Long idPedido);
    List<ItemPedidoDto> buscarItemPedidoByidProducto(Long idProducto);
    Double sumaTotalVentasDeidProducto(Long idProducto);
}
