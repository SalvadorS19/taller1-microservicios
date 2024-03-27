package com.taller1.microservicios.dto.Pedido;
import com.taller1.microservicios.dto.Pedido.PedidoDto;
import com.taller1.microservicios.dto.Pedido.PedidoToSaveDto;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.repository.ClienteRepository;
import com.taller1.microservicios.service.cliente.ClienteService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ClienteService.class})
public interface PedidoMapper {
    @Mapping(source = "cliente.id", target = "clienteId")
    PedidoDto pedidoToPedidoDto(Pedido pedido);
    @Mapping(source = "clienteId", target = "cliente")
    Pedido pedidoDtoToPedido(PedidoDto pedidoDto);
    @Mapping(source = "clienteId", target = "cliente")
    Pedido PedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto);
}