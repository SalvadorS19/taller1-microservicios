package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.itemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.pedido.*;
import com.taller1.microservicios.dto.pedido.PedidoDto;
import com.taller1.microservicios.model.*;
import com.taller1.microservicios.model.enums.EstadoPedido;
import com.taller1.microservicios.repository.ClienteRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import com.taller1.microservicios.service.pedido.PedidoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PedidoMapper pedidoMapper;
    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Cliente cliente;
    private List<ItemPedido> itemsPedido;

    private Pago pago;

    private DetalleEnvio detalleEnvio;

    @BeforeEach
    void setUp() {
        itemsPedido = new ArrayList<>();
        itemsPedido.add(ItemPedido.builder()
                .id(1L)
                .cantidad(5)
                .precioUnitario(200d)
                .build()
        );
       cliente = Cliente.builder().id(1L).build();
       pago = Pago.builder().id(1L).build();
       detalleEnvio = DetalleEnvio.builder().id(1L).build();
    }


    Pedido getPedidoMock() {
        return Pedido.builder()
                .id(1L)
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();
    }

    List<Pedido> getPedidoListMock() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(Pedido.builder()
                .id(1L)
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build()
        );
        pedidos.add(Pedido.builder()
                .id(2L)
                .fechaPedido(LocalDateTime.of(2024, Month.MARCH, 25, 12, 0))
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build()
        );
        return pedidos;
    }

    @Test
    void givenPedido_whenCrearPedido_thenReturnPedidoDto() {
        //Given
        Pedido pedido = getPedidoMock();
        PedidoToSaveDto pedidoToSaveDto = new PedidoToSaveDto(
                this.cliente.getId()
        );
        PedidoDto pedidoDto = new PedidoDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getEstadoPedido(),
                this.cliente.getId(),
                this.pago.getId(),
                this.detalleEnvio.getId()
        );
        given(pedidoMapper.pedidoToSaveDtoToPedido(any(PedidoToSaveDto.class))).willReturn(pedido);
        given(pedidoMapper.pedidoToPedidoDto(any(Pedido.class))).willReturn(pedidoDto);
        given(pedidoRepository.save(any(Pedido.class))).willReturn(pedido);
        given(clienteRepository.findById(this.cliente.getId())).willReturn(Optional.of(this.cliente));
        //When
        PedidoDto createdPedido = pedidoService.crearPedido(pedidoToSaveDto);
        //Then
        Assertions.assertNotNull(createdPedido);
        Assertions.assertEquals(createdPedido.id(), pedido.getId());
    }

    @Test
    void givenPedido_whenUpdate_returnUpdatedPedidoDto() {
        Long pedidoId = 1L;
        Pedido pedidoMock = getPedidoMock();
        Pedido updatedPedido = Pedido.builder()
                .id(1L)
                .fechaPedido(LocalDateTime.of(2023, Month.DECEMBER, 30, 12, 0))
                .estadoPedido(EstadoPedido.ENVIADO)
                .itemsPedido(this.itemsPedido)
                .cliente(this.cliente)
                .detalleEnvio(this.detalleEnvio)
                .pago(this.pago)
                .build();
        PedidoDto pedidoDto = new PedidoDto(
                updatedPedido.getId(),
                updatedPedido.getFechaPedido(),
                updatedPedido.getEstadoPedido(),
                this.cliente.getId(),
                this.pago.getId(),
                this.detalleEnvio.getId()
        );
        PedidoToUpdateDto pedidoToUpdate = new PedidoToUpdateDto(
                updatedPedido.getEstadoPedido()
        );
        given(pedidoRepository.findById(pedidoId)).willReturn(Optional.of(pedidoMock));
        given(pedidoRepository.save(any(Pedido.class))).willReturn(updatedPedido);
        given(pedidoMapper.pedidoToPedidoDto(any(Pedido.class))).willReturn(pedidoDto);
        PedidoDto updatedPedidoInService = pedidoService.actualizarPedido(pedidoId, pedidoToUpdate);
        Assertions.assertNotNull(updatedPedidoInService);
        Assertions.assertEquals(updatedPedidoInService.id(), pedidoId);
    }

    @Test
    void givenId_whenFindById_returnPedidoDto() {
        Long pedidoId = 1L;
        Pedido pedidoMock = getPedidoMock();
        PedidoDto pedidoDto = new PedidoDto(
                pedidoMock.getId(),
                pedidoMock.getFechaPedido(),
                pedidoMock.getEstadoPedido(),
                this.cliente.getId(),
                this.pago.getId(),
                this.detalleEnvio.getId()
        );
        given(pedidoRepository.findById(pedidoId)).willReturn(Optional.of(pedidoMock));
        given(pedidoMapper.pedidoToPedidoDto(any(Pedido.class))).willReturn(pedidoDto);
        PedidoDto foundPedido = pedidoService.buscarPedidoById(pedidoId);
        Assertions.assertNotNull(foundPedido);
        Assertions.assertEquals(pedidoId, foundPedido.id());
    }

    @Test
    void whenFindByAll_returnPedidoDtoList() {
        List<Pedido> pedidos = getPedidoListMock();
        List<PedidoDto> pedidoDtos = pedidos.stream().map(pedido -> new PedidoDto(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getEstadoPedido(),
                this.cliente.getId(),
                this.pago.getId(),
                this.detalleEnvio.getId()
        )).toList();
        given(pedidoRepository.findAll()).willReturn(pedidos);
        given(pedidoMapper.pedidoListToPedidoDtoList(any())).willReturn(pedidoDtos);
        List<PedidoDto> pedidosDtosService = pedidoService.getAllPedidos();
        Assertions.assertFalse(pedidosDtosService.isEmpty());
        Assertions.assertEquals(pedidosDtosService.size(), 2);
    }

    @Test
    void givenId_whenDelete_DeletePedido() {
        Long pedidoId = 1L;
        Pedido pedido = getPedidoMock();
        given(pedidoRepository.findById(pedidoId)).willReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).delete(pedido);
        pedidoService.removerPedido(pedidoId);
        Assertions.assertAll(() -> pedidoService.removerPedido(pedidoId));
    }

    @Test
    void whenFindByRangoFechaPedido_returnPedidoDtoList() {
        String fechaInicioStr = "2024-01-01 12:00";
        String fechaFinStr = "2024-06-30 12:00";
        List<Pedido> pedidosMock = getPedidoListMock();
        List<PedidoDto> pedidosDtoMock = pedidosMock.stream().map(pedido ->
                new PedidoDto(
                        pedido.getId(),
                        pedido.getFechaPedido(),
                        pedido.getEstadoPedido(),
                        this.cliente.getId(),
                        this.pago.getId(),
                        this.detalleEnvio.getId()
                )
        ).toList();
        given(pedidoRepository.findByFechaPedidoBetween(any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(pedidosMock);
        given(pedidoMapper.pedidoListToPedidoDtoList(any())).willReturn(pedidosDtoMock);
        List<PedidoDto> foundPedidos = pedidoService.buscarPedidosByRangoFechas(fechaInicioStr, fechaFinStr);
        Assertions.assertFalse(foundPedidos.isEmpty());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(PedidoDto pedido : foundPedidos) {
            LocalDateTime fechaPedido = pedido.fechaPedido();
            LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr, formatter);
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);
            Assertions.assertTrue(fechaPedido.isAfter(fechaInicio) && fechaPedido.isBefore(fechaFin));
        }
    }

    @Test
    void whenFindByClienteId_returnPedidoDto() {
        Long clienteId = 1L;
        EstadoPedido estadoPedido = EstadoPedido.PENDIENTE;
        List<Pedido> pedidoListMock = getPedidoListMock();
        List<PedidoDto> pedidoDtoListMock = pedidoListMock.stream().map(pedido ->
                new PedidoDto(
                        pedido.getId(),
                        pedido.getFechaPedido(),
                        pedido.getEstadoPedido(),
                        this.cliente.getId(),
                        this.pago.getId(),
                        this.detalleEnvio.getId()
                )
        ).toList();
        given(pedidoRepository.findByClienteIdAndEstadoPedido(clienteId, estadoPedido)).willReturn(pedidoListMock);
        given(pedidoMapper.pedidoListToPedidoDtoList(any())).willReturn(pedidoDtoListMock);
        List<PedidoDto> foundPedido = pedidoService.buscarPedidoByClienteIdAndEstado(clienteId, estadoPedido);
        Assertions.assertFalse(foundPedido.isEmpty());
        for (PedidoDto pedidoDto : foundPedido) {
            Assertions.assertEquals(pedidoDto.clienteId(), clienteId);
            Assertions.assertEquals(pedidoDto.estadoPedido(), estadoPedido);
        }
    }

    @Test
    void whenFindByClienteId_returnPedidoProductosDto() {
        Long clienteId = 1L;
        List<Pedido> pedidoListMock = getPedidoListMock();
        List<ItemPedidoDto> itemPedidoDtos = this.itemsPedido.stream().map(itemPedido ->
                new ItemPedidoDto(
                        itemPedido.getId(),
                        itemPedido.getCantidad(),
                        itemPedido.getPrecioUnitario(),
                        1L,
                        1L
                )
        ).toList();
        List<PedidoProductosDto> pedidoDtoListMock = pedidoListMock.stream().map(pedido ->
                new PedidoProductosDto(
                        pedido.getId(),
                        pedido.getFechaPedido(),
                        pedido.getEstadoPedido(),
                        this.cliente.getId(),
                        this.pago.getId(),
                        this.detalleEnvio.getId(),
                        itemPedidoDtos
                )
        ).toList();
        given(pedidoRepository.findPedidoConProductosByCliente(clienteId)).willReturn(pedidoListMock);
        given(pedidoMapper.pedidoListToPedidoProductosListDto(any())).willReturn(pedidoDtoListMock);
        List<PedidoProductosDto> foundPedido = pedidoService.buscarPedidosConProductos(clienteId);
        Assertions.assertFalse(foundPedido.isEmpty());
        for (PedidoProductosDto pedidoDto : foundPedido) {
            Assertions.assertEquals(pedidoDto.clienteId(), clienteId);
        }
    }
}
