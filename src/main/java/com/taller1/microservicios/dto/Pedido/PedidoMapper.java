package com.taller1.microservicios.dto.Pedido;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = EstadoPedido.class)
public interface PedidoMapper {
    @Mapping(source = "cliente.id", target = "clienteId")
    PedidoDto pedidoToPedidoDto(Pedido pedido);
    Pedido pedidoDtoToPedido(PedidoDto pedidoDto);
    default Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto) {
        return Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();
    }
    List<PedidoDto> pedidoListToPedidoDtoList(List<Pedido> pedidos);

}