package com.taller1.microservicios.service.ItemPedido;

import com.taller1.microservicios.dto.itemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoUpdateDto;
import com.taller1.microservicios.exception.DetalleEnvioNotFoundException;
import com.taller1.microservicios.exception.ItemPedidoNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.exception.ProductoNotFoundException;

import java.util.List;

public interface ItemPedidoService {
    ItemPedidoDto crearItemPedido (ItemPedidoToSaveDto itemPedidoToSaveDto) throws RuntimeException;
    ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoUpdateDto itemPedidoUpdateDto) throws ItemPedidoNotFoundException;
    ItemPedidoDto buscarItemPedidoById(Long id) throws ItemPedidoNotFoundException;
    void removerItemPedido(Long id) throws ItemPedidoNotFoundException;
    List<ItemPedidoDto> getAllItemPedidos();
    List<ItemPedidoDto> buscarItemPedidoByidPedido(Long idPedido);
    List<ItemPedidoDto> buscarItemPedidoByidProducto(Long idProducto);
    Double sumaTotalVentasDeidProducto(Long idProducto) throws RuntimeException;
}
