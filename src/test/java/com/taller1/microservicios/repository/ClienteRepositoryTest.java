package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

}
