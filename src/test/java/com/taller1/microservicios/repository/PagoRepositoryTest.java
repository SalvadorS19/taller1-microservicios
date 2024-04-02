package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.model.Pago;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import com.taller1.microservicios.model.enums.MetodoPago;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PagoRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;


    @BeforeEach
    void setUp() {
        pagoRepository.deleteAll();
    }

    Pago getPagoMock() {

        Cliente cliente = Cliente.builder().build();
        cliente = clienteRepository.save(cliente);

        Pedido pedido = Pedido.builder().cliente(cliente).build();
        pedido = pedidoRepository.save(pedido);

        return Pago.builder()
                .id(1L)
                .fechaPago(LocalDateTime.of(2024,Month.MARCH,29,12,0))
                .totalPago(2000d)
                .metodoPago(MetodoPago.PSE)
                .pedido(pedido)
                .build();

    }

    List<Pago> getPagoListMock() {
        List<Pago> pagos = new ArrayList<>();
        pagos.add(Pago.builder()
                .fechaPago(LocalDateTime.of(2024, Month.MARCH,29,12,0))
                .totalPago(2000d)
                .metodoPago(MetodoPago.PSE)
                .build()
        );
        pagos.add(Pago.builder()
                .fechaPago(LocalDateTime.of(2024,Month.FEBRUARY,25,12,0))
                .totalPago(1500d)
                .metodoPago(MetodoPago.NEQUI)
                .build()
        );
        return pagos;
    }

    @Test
    @DisplayName("Guardar un pago")
    void givenPago_savePago_thenReturnSavedPago() {
        //Given
        Pago pago = getPagoMock();
        //When
        Pago pagoSaved = pagoRepository.save(pago);
        //Then
        Assertions.assertNotNull(pagoSaved);
        Assertions.assertTrue(pagoSaved.getId() > 0);
        Assertions.assertEquals(pagoSaved.getFechaPago(), pago.getFechaPago());
        Assertions.assertEquals(pagoSaved.getMetodoPago(), pago.getMetodoPago());
        Assertions.assertEquals(pagoSaved.getTotalPago(), pago.getTotalPago());
    }

    @Test
    @DisplayName("Buscar todos los pagos")
    void givenPagoList_whenFindAll_thenPagoList() {
        //Given
        List<Pago> pagos = getPagoListMock();
        pagoRepository.saveAll(pagos);
        //When
        List<Pago> foundPagos = pagoRepository.findAll();
        //Then
        Assertions.assertNotNull(foundPagos);
        Assertions.assertEquals(foundPagos.size(), 2);
    }

    @Test
    @DisplayName("Buscar pago por id")
    void givenPago_whenFindById_thenPago() {
        //Given
        Pago pagoSaved = pagoRepository.save(getPagoMock());
        //When
        Optional<Pago> foundPago = pagoRepository.findById(pagoSaved.getId());
        //Then
        Assertions.assertTrue(foundPago.isPresent());
        Assertions.assertEquals(foundPago.get().getId(), pagoSaved.getId());
    }

    @Test
    @DisplayName("Eliminar un pago")
    void givenPago_whenDelete_thenRemovePago() {
        //Given
        Pago pagoSaved = pagoRepository.save(getPagoMock());
        //When
        pagoRepository.delete(pagoSaved);
        Optional<Pago> removedPago = pagoRepository.findById(pagoSaved.getId());
        //Then
        Assertions.assertFalse(removedPago.isPresent());
    }

    @Test
    @DisplayName("Actualizar un pago")
    void givenPago_whenUpdate_thenPago() {
        //Given
        Pago pagoSaved = pagoRepository.save(getPagoMock());
        //When
        Double nuevoTotalPago = 600d;
        LocalDateTime nuevaFechaPago = LocalDateTime.of(2024,Month.JANUARY,10,12,0);
        MetodoPago nuevoMetodoPago = MetodoPago.DAVIPLATA;
        pagoSaved.setTotalPago(nuevoTotalPago);
        pagoSaved.setFechaPago(nuevaFechaPago);
        pagoSaved.setMetodoPago(nuevoMetodoPago);
        Pago pagoUpdated = pagoRepository.save(pagoSaved);
        //Then
        Assertions.assertNotNull(pagoUpdated);
        Assertions.assertEquals(pagoSaved.getId(), pagoUpdated.getId());
        Assertions.assertEquals(pagoUpdated.getFechaPago(), nuevaFechaPago);
        Assertions.assertEquals(pagoUpdated.getMetodoPago(), nuevoMetodoPago);
        Assertions.assertEquals(pagoUpdated.getTotalPago(), nuevoTotalPago);
    }

    @Test
    @DisplayName("Buscar pagos entre dos fechas para fecha pago")
    void givenFechasRango_whenFindBetween_thenReturnPagos() {
        //Given
        List<Pago> productos = pagoRepository.saveAll(getPagoListMock());
        //When
        LocalDateTime fechaInicio = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2024, Month.JULY, 1, 12, 0);
        List<Pago> foundPagos = pagoRepository.findByFechaPagoIsBetween(fechaInicio, fechaFin);
        //Then
        Assertions.assertFalse(foundPagos.isEmpty());
        for(Pago pago : foundPagos) {
            LocalDateTime fechaPago = pago.getFechaPago();
            Assertions.assertTrue(fechaPago.isAfter(fechaInicio) && fechaPago.isBefore(fechaFin));
        }
    }

    @Test
    @DisplayName("Buscar por idpedido")
    void given(){
        //Given
        Pago pago = pagoRepository.save(getPagoMock());
        Long idPedido = pago.getPedido().getId();
        //When
        Optional<Pago> foundPago = pagoRepository.findByPedidoId(idPedido);
        //Then
        Assertions.assertTrue(foundPago.isPresent());
    }

}
