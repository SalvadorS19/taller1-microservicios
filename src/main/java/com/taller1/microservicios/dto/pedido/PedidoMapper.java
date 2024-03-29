package com.taller1.microservicios.dto.pedido;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoMapper;
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
    @Mapping(source = "pago.id", target = "pagoId")
    @Mapping(source = "detalleEnvio.id", target = "detalleEnvioId")
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
                pedido -> {
                    Long pagoId = null;
                    Long detalleEnvioId = null;
                    if (pedido.getPago() != null) {
                        pagoId = pedido.getPago().getId();
                    }
                    if (pedido.getDetalleEnvio() != null) {
                        detalleEnvioId = pedido.getDetalleEnvio().getId();
                    }
                    return new PedidoProductosDto(
                            pedido.getId(),
                            pedido.getFechaPedido(),
                            pedido.getEstadoPedido(),
                            pedido.getCliente().getId(),
                            pagoId,
                            detalleEnvioId,
                            itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedido.getItemsPedido())
                    );
                }
        ).toList();
    }
}