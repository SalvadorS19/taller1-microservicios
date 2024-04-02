package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.cliente.ClienteDto;
import com.taller1.microservicios.dto.producto.ProductoDto;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.repository.ClienteRepository;
import com.taller1.microservicios.service.cliente.ClienteService;
import com.taller1.microservicios.service.cliente.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    Cliente getClienteMock() {
        return Cliente.builder()
                    .nombre("Juan")
                    .email("juan@gmail.com")
                    .direccion("Calle ABC")
                    .build();

    }

}
