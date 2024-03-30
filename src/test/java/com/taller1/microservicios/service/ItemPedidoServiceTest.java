package com.taller1.microservicios.service;

import com.taller1.microservicios.repository.ItemPedidoRepository;
import com.taller1.microservicios.service.ItemPedido.ItemPedidoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemPedidoServiceTest {

    @Mock
    ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    ItemPedidoService itemPedidoService;
}
