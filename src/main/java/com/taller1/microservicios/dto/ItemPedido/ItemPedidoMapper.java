package com.taller1.microservicios.dto.ItemPedido;

import com.taller1.microservicios.model.ItemPedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface ItemPedidoMapper {
    ItemPedidoDto ItemPedidoToItemPedidoDto(ItemPedido ItemPedido);
    ItemPedido ItemPedidoDtoToCliente(ItemPedidoDto ItemPedidoDto);
    ItemPedido ItemPedidoToSaveDtoToItemPedido(ItemPedidoToSaveDto ItemPedidoToSaveDto);
    List<ItemPedidoDto> itemPedidoListToItemPedidoDtoList(List<ItemPedido> itemPedidos);
}
