package com.taller1.microservicios.dto.itemPedido;

import com.taller1.microservicios.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface ItemPedidoMapper {

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "producto.id", target = "productoId")
    ItemPedidoDto ItemPedidoToItemPedidoDto(ItemPedido ItemPedido);
    ItemPedido ItemPedidoToSaveDtoToItemPedido(ItemPedidoToSaveDto ItemPedidoToSaveDto);
    List<ItemPedidoDto> itemPedidoListToItemPedidoDtoList(List<ItemPedido> itemPedidos);
}
