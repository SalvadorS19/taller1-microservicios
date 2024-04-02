package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.model.ItemPedido;
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
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @BeforeEach
    void setUp() { pedidoRepository.deleteAll(); }

    Pedido getPedidoMock() {
        Cliente cliente = Cliente.builder().build();
        cliente = clienteRepository.save(cliente);

        return Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .cliente(cliente)
                .build();
    }

    List<Pedido> getPedidoListMock() {

        Cliente cliente = Cliente.builder().build();
        cliente = clienteRepository.save(cliente);

        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .cliente(cliente)
                .build()
        );
        pedidos.add(Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.FEBRUARY, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .cliente(cliente)
                .build()
        );
        pedidos.add(Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, Month.DECEMBER, 25, 12, 0))
                .estadoPedido(EstadoPedido.ENVIADO)
                .cliente(cliente)
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

    @Test
    @DisplayName("Buscar pedido por idcliente y estado del pedido")
        void givenIdCLienteAndStatus_WhenFindIdClienteAndStatus_thenPedidos(){
        //Given
        pedidoRepository.saveAll(getPedidoListMock());
        Long idCliente = 1L;
        EstadoPedido estado = EstadoPedido.PENDIENTE;
        //When
        List<Pedido> foundPedidos = pedidoRepository.findByClienteIdAndEstadoPedido(idCliente, estado);
        //Then
        Assertions.assertFalse(foundPedidos.isEmpty());
    }

    @Test
    @DisplayName("Buscar pedido con productos por idcliente ")
    void givenIdCLiente_WhenFindIdClienteConProductos_thenPedidos(){
        //Given
        Pedido pedido = getPedidoMock();
        pedido= pedidoRepository.save(pedido);
        List<ItemPedido> itemPedidos = new ArrayList<>();
        itemPedidos.add(ItemPedido.builder().pedido(pedido).build());
        itemPedidos.add(ItemPedido.builder().pedido(pedido).build());
        itemPedidos= itemPedidoRepository.saveAll(itemPedidos);
        ItemPedido item = itemPedidos.get(0);
        item.setPedido(pedido);
        itemPedidoRepository.save(item);
        Long idCliente = 1L;
        //When
        List<Pedido> foundPedidos = pedidoRepository.findPedidoConProductosByCliente(idCliente);
        //Then
        Assertions.assertFalse(foundPedidos.isEmpty());
    }

}
