package com.taller1.microservicios.dto.cliente;

import com.taller1.microservicios.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDto clienteToClienteDto(Cliente cliente);
    Cliente clienteToSaveDtoToCliente(ClienteToSaveDto clienteToSaveDto);

    List<ClienteDto> clienteListToClienteDtoList(List<Cliente> clientes);
}
