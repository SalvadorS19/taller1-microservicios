package com.taller1.microservicios.dto.Cliente;

import com.taller1.microservicios.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDto clienteToClienteDto(Cliente cliente);
    Cliente clienteDtoToCliente(ClienteDto clienteDto);
    Cliente clienteToSaveDtoToCliente(ClienteToSaveDto clienteToSaveDto);
}
