package com.taller1.microservicios.service;

import com.taller1.microservicios.repository.PedidoRepository;
import com.taller1.microservicios.service.pedido.PedidoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    PedidoRepository pedidoRepository;

    @InjectMocks
    PedidoService pedidoService;
}
