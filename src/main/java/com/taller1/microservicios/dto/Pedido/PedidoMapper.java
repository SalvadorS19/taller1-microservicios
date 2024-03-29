package com.taller1.microservicios.dto.Pedido;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoMapper;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    ItemPedidoMapper itemPedidoMapper = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "cliente.id", target = "clienteId")
    PedidoDto pedidoToPedidoDto(Pedido pedido);
    default Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto) {
        return Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();
    }
    List<PedidoDto> pedidoListToPedidoDtoList(List<Pedido> pedidos);

    default List<PedidoProductosDto> pedidoListToPedidoProductosListDto(List<Pedido> pedidos) {
        return pedidos.stream().map(
                pedido -> new PedidoProductosDto(
                        pedido.getId(),
                        pedido.getFechaPedido(),
                        pedido.getEstadoPedido(),
                        pedido.getCliente().getId(),
                        itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedido.getItemsPedido())
                )
        ).toList();
    }
}