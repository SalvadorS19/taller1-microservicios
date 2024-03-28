package com.taller1.microservicios.dto.ItemPedido;

import com.taller1.microservicios.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface ItemPedidoMapper {
    ItemPedidoDto ItemPedidoToItemPedidoDto(ItemPedido ItemPedido);
    ItemPedido ItemPedidoToSaveDtoToItemPedido(ItemPedidoToSaveDto ItemPedidoToSaveDto);
    List<ItemPedidoDto> itemPedidoListToItemPedidoDtoList(List<ItemPedido> itemPedidos);
}
