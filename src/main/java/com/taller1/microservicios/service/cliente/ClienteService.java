package com.taller1.microservicios.service.cliente;

import com.taller1.microservicios.dto.cliente.ClienteDto;
import com.taller1.microservicios.dto.cliente.ClienteToSaveDto;

import java.util.List;

public interface ClienteService {

    ClienteDto crearCliente(ClienteToSaveDto clienteToSaveDto);

    ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteToSaveDto);

    ClienteDto buscarClienteById(Long id);

    void removerCliente(Long id);

    List<ClienteDto> getAllClientes();

    ClienteDto buscarClienteByEmail(String email);

    ClienteDto buscarClienteByDireccion(String direccion);

    List<ClienteDto> buscarClientesNombreEmpiezaPor(String nombre);

}
