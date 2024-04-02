package com.taller1.microservicios.service.cliente;

import com.taller1.microservicios.dto.cliente.ClienteDto;
import com.taller1.microservicios.dto.cliente.ClienteMapper;
import com.taller1.microservicios.dto.cliente.ClienteToSaveDto;
import com.taller1.microservicios.exception.ClienteNotFoundException;
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
        cliente = this.clienteRepository.save(cliente);
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteToSaveDto) throws ClienteNotFoundException {
        Cliente clienteInDb = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente no existe"));
        clienteInDb.setNombre(clienteToSaveDto.nombre());
        clienteInDb.setEmail(clienteToSaveDto.email());
        clienteInDb.setDireccion(clienteToSaveDto.direccion());
        clienteInDb = clienteRepository.save(clienteInDb);
        return this.clienteMapper.clienteToClienteDto(clienteInDb);
    }

    @Override
    public ClienteDto buscarClienteById(Long id) throws ClienteNotFoundException {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(()-> new ClienteNotFoundException("El cliente no existe"));
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public void removerCliente(Long id) throws ClienteNotFoundException {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente no existe"));
        this.clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteDto> getAllClientes() {
        List<Cliente> clientes = this.clienteRepository.findAll();
        return this.clienteMapper.clienteListToClienteDtoList(clientes);
    }

    @Override
    public ClienteDto buscarClienteByEmail(String email) throws ClienteNotFoundException {
        Cliente cliente = this.clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente no existe"));
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public ClienteDto buscarClienteByDireccion(String direccion) throws ClienteNotFoundException {
        Cliente cliente = this.clienteRepository.findByDireccion(direccion)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente no existe"));
        return this.clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> buscarClientesNombreEmpiezaPor(String nombre) {
        List<Cliente> clientes = this.clienteRepository.findByNombreStartingWithIgnoreCase(nombre);
        return this.clienteMapper.clienteListToClienteDtoList(clientes);
    }
}
