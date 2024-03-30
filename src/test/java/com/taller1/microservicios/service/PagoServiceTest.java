package com.taller1.microservicios.service;

import com.taller1.microservicios.repository.PagoRepository;
import com.taller1.microservicios.service.pago.PagoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    PagoRepository pagoRepository;

    @InjectMocks
    PagoService pagoService;
}
