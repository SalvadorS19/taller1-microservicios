package com.taller1.microservicios.service.cliente;

import com.taller1.microservicios.dto.Cliente.ClienteDto;
import com.taller1.microservicios.dto.Cliente.ClienteToSaveDto;
import com.taller1.microservicios.model.Cliente;

import java.util.List;

public interface ClienteService {

    ClienteDto crearCliente(ClienteToSaveDto clienteToSaveDto);

    ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteToSaveDto);

    ClienteDto buscarClienteById(Long id); // Agregar exception

    void removerCliente(Long id);

    List<ClienteDto> getAllClientes();

    ClienteDto buscarClienteByEmail(String email);

    ClienteDto buscarClienteByDireccion(String direccion);

    List<ClienteDto> buscarClientesNombreEmpiezaPor(String nombre);



}
