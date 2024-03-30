package com.taller1.microservicios.service;

import com.taller1.microservicios.repository.DetalleEnvioRepository;
import com.taller1.microservicios.service.detalleEnvio.DetalleEnvioService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DetalleEnvioServiceTest {

    @Mock
    DetalleEnvioRepository detalleEnvioRepository;

    @InjectMocks
    DetalleEnvioService detalleEnvioService;
}
