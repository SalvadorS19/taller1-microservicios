package com.taller1.microservicios.dto.ItemPedido;

import com.taller1.microservicios.dto.ItemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface ItemPedidoMapper {
    ItemPedidoDto ItemPedidoToItemPedidoDto(ItemPedido ItemPedido);
    ItemPedido ItemPedidoDtoToCliente(ItemPedidoDto ItemPedidoDto);
    ItemPedido ItemPedidoToSaveDtoToItemPedido(ItemPedidoToSaveDto ItemPedidoToSaveDto);
}
