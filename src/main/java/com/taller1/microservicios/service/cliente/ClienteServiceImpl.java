package com.taller1.microservicios.service.cliente;

import com.taller1.microservicios.dto.Cliente.ClienteDto;
import com.taller1.microservicios.dto.Cliente.ClienteMapper;
import com.taller1.microservicios.dto.Cliente.ClienteToSaveDto;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteMapper clienteMapper;

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteMapper clienteMapper, ClienteRepository clienteRepository) {
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDto crearCliente(ClienteToSaveDto clienteToSaveDto) {
        Cliente cliente = this.clienteMapper.clienteToSaveDtoToCliente(clienteToSaveDto);
        this.clienteRepository.save(cliente);
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteToSaveDto) {
        return clienteRepository.findById(id).map(clienteInDb -> {
            clienteInDb.setNombre(clienteToSaveDto.nombre());
            clienteInDb.setEmail(clienteToSaveDto.email());
            clienteInDb.setDireccion(clienteToSaveDto.direccion());

            clienteRepository.save(clienteInDb);
            return this.clienteMapper.clienteToClienteDto(clienteInDb);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public ClienteDto buscarClienteById(Long id) {
        return null;
    }

    @Override
    public void removerCliente(Long id) {

    }

    @Override
    public List<ClienteDto> getAllClientes() {
        return null;
    }

    @Override
    public ClienteDto buscarClienteByEmail(String email) {
        return null;
    }

    @Override
    public ClienteDto buscarClienteByDireccion(String direccion) {
        return null;
    }

    @Override
    public List<Cliente> buscarClientesNombreEmpiezaPor(String nombre) {
        return null;
    }
}
