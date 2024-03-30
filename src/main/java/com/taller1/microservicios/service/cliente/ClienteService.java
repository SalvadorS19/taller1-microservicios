package com.taller1.microservicios.service.cliente;

import com.taller1.microservicios.dto.cliente.ClienteDto;
import com.taller1.microservicios.dto.cliente.ClienteToSaveDto;
import com.taller1.microservicios.exception.ClienteNotFoundException;

import java.util.List;

public interface ClienteService {

    ClienteDto crearCliente(ClienteToSaveDto clienteToSaveDto);

    ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteToSaveDto) throws ClienteNotFoundException;

    ClienteDto buscarClienteById(Long id) throws ClienteNotFoundException;

    void removerCliente(Long id) throws ClienteNotFoundException;

    List<ClienteDto> getAllClientes();

    ClienteDto buscarClienteByEmail(String email) throws ClienteNotFoundException;

    ClienteDto buscarClienteByDireccion(String direccion) throws ClienteNotFoundException;

    List<ClienteDto> buscarClientesNombreEmpiezaPor(String nombre);

}
