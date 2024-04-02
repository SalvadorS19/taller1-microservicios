package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.cliente.ClienteMapper;
import com.taller1.microservicios.dto.cliente.ClienteDto;
import com.taller1.microservicios.dto.cliente.ClienteToSaveDto;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.repository.ClienteRepository;
import com.taller1.microservicios.service.cliente.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith({MockitoExtension.class})
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    Cliente getClienteMock() {
        return Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .email("juan@gmail.com")
                .direccion("Calle ABC")
                .build();
    }

    List<Cliente> getClienteListMock() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(
                Cliente.builder()
                        .id(1L)
                        .nombre("Juan")
                        .email("juan@gmail.com")
                        .direccion("Calle ABC")
                        .build()
        );
        clientes.add(
                Cliente.builder()
                        .id(2L)
                        .nombre("Sofia")
                        .email("sofia@gmail.com")
                        .direccion("Calle 123")
                        .build()
        );
        return clientes;
    }

    @Test
    void givenCliente_whenCrearCliente_thenReturnClienteDto() {
        //Given
        Cliente cliente = getClienteMock();
        ClienteToSaveDto clienteToSaveDto = new ClienteToSaveDto(
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        ClienteDto clienteDto = new ClienteDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteMapper.clienteToSaveDtoToCliente(any(ClienteToSaveDto.class))).willReturn(cliente);
        given(clienteMapper.clienteToClienteDto(any(Cliente.class))).willReturn(clienteDto);
        given(clienteRepository.save(any(Cliente.class))).willReturn(cliente);
        //When
        ClienteDto createdCliente = clienteService.crearCliente(clienteToSaveDto);
        //Then
        Assertions.assertNotNull(createdCliente);
        Assertions.assertEquals(createdCliente.id(), cliente.getId());
    }

    @Test
    void givenCliente_whenUpdate_returnUpdatedClienteDto() {
        Long clienteId = 1L;
        Cliente clienteMock = getClienteMock();
        Cliente updatedCliente = Cliente.builder()
                .id(clienteId)
                .nombre("Daniel")
                .email("daniel@gmail.com")
                .direccion("Calle 16A")
                .build();
        ClienteDto clienteDto = new ClienteDto(
                updatedCliente.getId(),
                updatedCliente.getNombre(),
                updatedCliente.getEmail(),
                updatedCliente.getDireccion()
        );
        ClienteToSaveDto clienteToUpdate = new ClienteToSaveDto(
                updatedCliente.getNombre(),
                updatedCliente.getEmail(),
                updatedCliente.getDireccion()
        );
        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(clienteMock));
        given(clienteRepository.save(any(Cliente.class))).willReturn(updatedCliente);
        given(clienteMapper.clienteToClienteDto(any(Cliente.class))).willReturn(clienteDto);
        ClienteDto updatedClienteInService = clienteService.actualizarCliente(clienteId, clienteToUpdate);
        Assertions.assertNotNull(updatedClienteInService);
        Assertions.assertEquals(updatedClienteInService.id(), clienteId);
    }

    @Test
    void givenId_whenFindById_returnClienteDto() {
        Long clienteId = 1L;
        Cliente clienteMock = getClienteMock();
        ClienteDto clienteDto = new ClienteDto(
                clienteMock.getId(),
                clienteMock.getNombre(),
                clienteMock.getEmail(),
                clienteMock.getDireccion()
        );
        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(clienteMock));
        given(clienteMapper.clienteToClienteDto(any(Cliente.class))).willReturn(clienteDto);
        ClienteDto foundCliente = clienteService.buscarClienteById(clienteId);
        Assertions.assertNotNull(foundCliente);
        Assertions.assertEquals(clienteId, foundCliente.id());
    }

    @Test
    void whenFindByAll_returnClienteDtoList() {
        List<Cliente> clientes = getClienteListMock();
        List<ClienteDto> clienteDtos = clientes.stream().map(cliente -> new ClienteDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        )).toList();
        given(clienteRepository.findAll()).willReturn(clientes);
        given(clienteMapper.clienteListToClienteDtoList(any())).willReturn(clienteDtos);
        List<ClienteDto> clientesDtosService = clienteService.getAllClientes();
        Assertions.assertFalse(clientesDtosService.isEmpty());
        Assertions.assertEquals(clientesDtosService.size(), 2);
    }

    @Test
    void givenId_whenDelete_DeleteCliente() {
        Long clienteId = 1L;
        Cliente cliente = getClienteMock();
        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);
        clienteService.removerCliente(clienteId);
        Assertions.assertAll(() -> clienteService.removerCliente(clienteId));
    }
    @Test
    void givenEmail_whenFindByEmail_returnClienteDto() {
        String email = "juan@gmail.com";
        Cliente clienteMock = getClienteMock();
        ClienteDto clienteDto = new ClienteDto(
                clienteMock.getId(),
                clienteMock.getNombre(),
                clienteMock.getEmail(),
                clienteMock.getDireccion()
        );
        given(clienteRepository.findByEmail(email)).willReturn(Optional.of(clienteMock));
        given(clienteMapper.clienteToClienteDto(any(Cliente.class))).willReturn(clienteDto);
        ClienteDto foundCliente = clienteService.buscarClienteByEmail(email);
        Assertions.assertNotNull(foundCliente);
        Assertions.assertEquals(clienteMock.getEmail(), foundCliente.email());
    }
    @Test
    void whenFindByDireccion_returnClienteDto() {
        String direccion = "Calle ABC";
        Cliente clienteMock = getClienteMock();
        ClienteDto clienteDto = new ClienteDto(
                clienteMock.getId(),
                clienteMock.getNombre(),
                clienteMock.getEmail(),
                clienteMock.getDireccion()
        );
        given(clienteRepository.findByDireccion(direccion)).willReturn(Optional.of(clienteMock));
        given(clienteMapper.clienteToClienteDto(any(Cliente.class))).willReturn(clienteDto);
        ClienteDto foundCliente = clienteService.buscarClienteByDireccion(direccion);
        Assertions.assertNotNull(foundCliente);
        Assertions.assertEquals(clienteMock.getDireccion(), foundCliente.direccion());
    }
    @Test
    void whenFindByNombreStartsWith_returnClienteDtoList() {
        String nombre = "sofi";
        List<Cliente> clientesMock = new ArrayList<>();
        clientesMock.add(Cliente.builder()
                .id(2L)
                .nombre("Sofia")
                .email("sofia@gmail.com")
                .direccion("Calle 123")
                .build()
        );
        List<ClienteDto> clientesDtoMock = clientesMock.stream().map(cliente ->
                new ClienteDto(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getDireccion()
                )
        ).toList();
        given(clienteRepository.findByNombreStartingWithIgnoreCase(nombre)).willReturn(clientesMock);
        given(clienteMapper.clienteListToClienteDtoList(any())).willReturn(clientesDtoMock);
        List<ClienteDto> foundClientes = clienteService.buscarClientesNombreEmpiezaPor(nombre);
        Assertions.assertFalse(foundClientes.isEmpty());
        for (ClienteDto cliente : foundClientes) {
            Assertions.assertTrue(cliente.nombre().toLowerCase().startsWith(nombre));
        }
    }
}
