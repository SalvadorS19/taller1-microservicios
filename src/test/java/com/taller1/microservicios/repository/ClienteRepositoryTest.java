package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    Cliente getClienteMock() {
        return Cliente.builder()
                .nombre("Juan")
                .email("juan@gmail.com")
                .direccion("Calle ABC")
                .build();
    }

    List<Cliente> getClienteListMock() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(
                Cliente.builder()
                .nombre("Juan")
                .email("juan@gmail.com")
                .direccion("Calle ABC")
                .build()
        );
        clientes.add(
                Cliente.builder()
                .nombre("Sofia")
                .email("sofia@gmail.com")
                .direccion("Calle 123")
                .build()
        );
        return clientes;
    }
    @Test
    @DisplayName("Guardar un objeto cliente")
    void givenCliente_saveCliente_thenReturnSavedCliente() {
        //Given
        Cliente cliente = getClienteMock();
        //When
        Cliente clienteSaved = clienteRepository.save(cliente);
        //Then
        Assertions.assertNotNull(clienteSaved);
        Assertions.assertTrue(clienteSaved.getId() > 0);
        Assertions.assertEquals(clienteSaved.getNombre(), cliente.getNombre());
        Assertions.assertEquals(clienteSaved.getDireccion(), cliente.getDireccion());
        Assertions.assertEquals(clienteSaved.getEmail(), cliente.getEmail());
    }

    @Test
    @DisplayName("Buscar todos los clientes")
    void givenClienteList_whenFindAll_thenClienteList() {
        //Given
        List<Cliente> clientes = getClienteListMock();
        clienteRepository.saveAll(clientes);
        //When
        List<Cliente> foundClientes = clienteRepository.findAll();
        //Then
        Assertions.assertNotNull(foundClientes);
        Assertions.assertEquals(foundClientes.size(), 2);
    }

    @Test
    @DisplayName("Buscar cliente por id")
    void givenCliente_whenFindById_thenCliente() {
        //Given
        Cliente clienteSaved = clienteRepository.save(getClienteMock());
        //When
        Optional<Cliente> foundCliente = clienteRepository.findById(clienteSaved.getId());
        //Then
        Assertions.assertTrue(foundCliente.isPresent());
        Assertions.assertEquals(foundCliente.get().getId(), clienteSaved.getId());
    }

    @Test
    @DisplayName("Eliminar un cliente")
    void givenCliente_whenDelete_thenRemoveCliente() {
        //Given
        Cliente clienteSaved = clienteRepository.save(getClienteMock());
        //When
        clienteRepository.delete(clienteSaved);
        Optional<Cliente> removedCliente = clienteRepository.findById(clienteSaved.getId());
        //Then
        Assertions.assertFalse(removedCliente.isPresent());
    }

    @Test
    @DisplayName("Actualizar un cliente")
    void givenCliente_whenUpdate_thenCliente() {
        //Given
        Cliente clienteSaved = clienteRepository.save(getClienteMock());
        //When
        String nuevoNombre = "Esteban";
        String nuevoEmail = "esteban@gmail.com";
        String nuevaDireccion = "Carrera 1";
        clienteSaved.setNombre(nuevoNombre);
        clienteSaved.setEmail(nuevoEmail);
        clienteSaved.setDireccion(nuevaDireccion);
        Cliente clienteUpdated = clienteRepository.save(clienteSaved);
        //Then
        Assertions.assertNotNull(clienteUpdated);
        Assertions.assertEquals(clienteSaved.getId(), clienteUpdated.getId());
        Assertions.assertEquals(clienteUpdated.getNombre(), nuevoNombre);
        Assertions.assertEquals(clienteUpdated.getEmail(), nuevoEmail);
        Assertions.assertEquals(clienteUpdated.getDireccion(), nuevaDireccion);
    }

    @Test
    @DisplayName("Buscar cliente por email")
    void givenClienteEmail_whenBuscarCliente_thenCliente() {
        //Given
        Cliente clienteSaved = clienteRepository.save(getClienteMock());
        String email = "juan@gmail.com";
        //When
        Optional<Cliente> clienteFound = clienteRepository.findByEmail(email);
        //Then
        Assertions.assertTrue(clienteFound.isPresent());
        Assertions.assertEquals(clienteFound.get().getEmail(), email);
    }

    @Test
    @DisplayName("Buscar cliente por direccion")
    void givenClienteDireccion_whenBuscarCliente_thenCliente() {
        //Given
        Cliente clienteSaved = clienteRepository.save(getClienteMock());
        String direccion = "Calle ABC";
        //When
        Optional<Cliente> clienteFound = clienteRepository.findByDireccion(direccion);
        //Then
        Assertions.assertTrue(clienteFound.isPresent());
        Assertions.assertEquals(clienteFound.get().getDireccion(), direccion);
    }

    @Test
    @DisplayName("Buscar cliente donde nombre empiece por el proporcionado")
    void givenClienteNombre_whenBuscarClienteNombreEmpiezaPor_thenCliente() {
        //Given
        clienteRepository.saveAll(this.getClienteListMock());
        String nombre = "sofi";
        //When
        List<Cliente> clientesFound = clienteRepository.findByNombreStartingWithIgnoreCase(nombre);
        //Then
        Assertions.assertFalse(clientesFound.isEmpty());
        for (Cliente cliente : clientesFound) {
            Assertions.assertTrue(cliente.getNombre()
                    .toLowerCase().startsWith(nombre)
            );
        }
    }
}
