package com.taller1.microservicios.dto.Pedido;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoMapper;
import com.taller1.microservicios.dto.Producto.ProductoMapper;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = ItemPedidoMapper.class)
public interface PedidoMapper {
    @Mapping(source = "cliente.id", target = "clienteId")
    PedidoDto pedidoToPedidoDto(Pedido pedido);
    default Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto) {
        return Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();
    }
    List<PedidoDto> pedidoListToPedidoDtoList(List<Pedido> pedidos);

}