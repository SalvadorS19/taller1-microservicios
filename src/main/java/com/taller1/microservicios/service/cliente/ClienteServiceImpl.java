package com.taller1.microservicios.service.cliente;

import com.taller1.microservicios.dto.Cliente.ClienteDto;
import com.taller1.microservicios.dto.Cliente.ClienteMapper;
import com.taller1.microservicios.dto.Cliente.ClienteToSaveDto;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public void removerCliente(Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontado"));
        this.clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteDto> getAllClientes() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        return clientes.stream().map(this.clienteMapper::clienteToClienteDto).toList();
    }

    @Override
    public ClienteDto buscarClienteByEmail(String email) {
        Cliente cliente = this.clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontado"));
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public ClienteDto buscarClienteByDireccion(String direccion) {
        Cliente cliente = this.clienteRepository.findByDireccion(direccion)
                .orElseThrow(() -> new RuntimeException("Cliente no encontado"));
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> buscarClientesNombreEmpiezaPor(String nombre) {
        List<Cliente> clientes = this.clienteRepository.findByNombreStartsWith(nombre);
        if (clientes.isEmpty()) {
            throw new RuntimeException("No existen clientes");
        } else {
            return clientes.stream().map(this.clienteMapper::clienteToClienteDto).toList();
        }

    }
}
