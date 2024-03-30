package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoRepositoryTest extends AbstractIntegrationDBTest {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() { pedidoRepository.deleteAll(); }

    Pedido getPedidoMock() {
        return Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();
    }

    List<Pedido> getPedidoListMock() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build()
        );
        pedidos.add(Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build()
        );
        return pedidos;
    }

    @Test
    @DisplayName("Guardar un pedido")
    void givenPedido_savePedido_thenReturnSavedPedido() {
        //Given
        Pedido pedido = getPedidoMock();
        //When
        Pedido pedidoSaved = pedidoRepository.save(pedido);
        //Then
        Assertions.assertNotNull(pedidoSaved);
        Assertions.assertTrue(pedidoSaved.getId() > 0);
        Assertions.assertEquals(pedidoSaved.getFechaPedido(), pedido.getFechaPedido());
        Assertions.assertEquals(pedidoSaved.getEstadoPedido(), pedido.getEstadoPedido());
    }

    @Test
    @DisplayName("Buscar todos los pedidos")
    void givenPedidoList_whenFindAll_thenPedidoList() {
        //Given
        List<Pedido> pedidos = getPedidoListMock();
        pedidoRepository.saveAll(pedidos);
        //When
        List<Pedido> foundPedidos = pedidoRepository.findAll();
        //Then
        Assertions.assertNotNull(foundPedidos);
        Assertions.assertEquals(foundPedidos.size(), 2);
    }

    @Test
    @DisplayName("Buscar pedido por id")
    void givenPedido_whenFindById_thenPedido() {
        //Given
        Pedido pedidoSaved = pedidoRepository.save(getPedidoMock());
        //When
        Optional<Pedido> foundPedido = pedidoRepository.findById(pedidoSaved.getId());
        //Then
        Assertions.assertTrue(foundPedido.isPresent());
        Assertions.assertEquals(foundPedido.get().getId(), pedidoSaved.getId());
    }

    @Test
    @DisplayName("Eliminar un pedido")
    void givenPedido_whenDelete_thenRemovePedido() {
        //Given
        Pedido pedidoSaved = pedidoRepository.save(getPedidoMock());
        //When
        pedidoRepository.delete(pedidoSaved);
        Optional<Pedido> removedPedido = pedidoRepository.findById(pedidoSaved.getId());
        //Then
        Assertions.assertFalse(removedPedido.isPresent());
    }

    @Test
    @DisplayName("Actualizar un pedido")
    void givenPedido_whenUpdate_thenPedido() {
        //Given
        Pedido pedidoSaved = pedidoRepository.save(getPedidoMock());
        //When
        LocalDateTime nuevaFechaPedido = LocalDateTime.of(2024, Month.APRIL, 14, 12, 30);
        EstadoPedido nuevoEstadoPedido = EstadoPedido.ENVIADO;
        pedidoSaved.setFechaPedido(nuevaFechaPedido);
        pedidoSaved.setEstadoPedido(nuevoEstadoPedido);
        Pedido pedidoUpdated = pedidoRepository.save(pedidoSaved);
        //Then
        Assertions.assertNotNull(pedidoUpdated);
        Assertions.assertEquals(pedidoSaved.getId(), pedidoUpdated.getId());
        Assertions.assertEquals(pedidoUpdated.getFechaPedido(), nuevaFechaPedido);
        Assertions.assertEquals(pedidoUpdated.getEstadoPedido(), nuevoEstadoPedido);
    }

    @Test
    @DisplayName("Buscar pedidos entre dos fechas para fecha pedido")
    void givenFechasRango_whenFindBetween_thenReturnPedidos() {
        //Given
        List<Pedido> pedidos = pedidoRepository.saveAll(getPedidoListMock());
        //When
        LocalDateTime fechaInicio = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2024, Month.JULY, 1, 12, 0);
        List<Pedido> foundPedidos = pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);
        //Then
        Assertions.assertFalse(foundPedidos.isEmpty());
        for(Pedido pedido : foundPedidos) {
            LocalDateTime fechaPedido = pedido.getFechaPedido();
            Assertions.assertTrue(fechaPedido.isAfter(fechaInicio) && fechaPedido.isBefore(fechaFin));
        }
    }

    // TOCA CREAR EL DE BUSCAR POR CLIENTE Y ESTADO
    // TOCA CREAR EL DE BUSCAR POR CLIENTE CON PRODUCTOS
}
