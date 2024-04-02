package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.pago.PagoDto;
import com.taller1.microservicios.dto.pago.PagoToSaveDto;
import com.taller1.microservicios.dto.pago.PagoMapper;
import com.taller1.microservicios.dto.pago.PagoUpdateDto;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.model.Pago;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.MetodoPago;
import com.taller1.microservicios.repository.PagoRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import com.taller1.microservicios.service.pago.PagoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PagoMapper pagoMapper;
    @InjectMocks
    private PagoServiceImpl pagoService;

    private Pedido pedido;
    private List<ItemPedido> itemsPedido;

    @BeforeEach
    void setUp() {
        itemsPedido = new ArrayList<>();
        itemsPedido.add(ItemPedido.builder()
                .id(1L)
                .cantidad(5)
                .precioUnitario(200d)
                .build()
        );
        pedido = Pedido.builder()
                .id(1L)
                .itemsPedido(this.itemsPedido)
                .build();
    }
    Pago getPagoMock() {
        return Pago.builder()
                .id(1L)
                .fechaPago(LocalDateTime.of(2024, Month.MARCH,29,12,0))
                .totalPago(2000d)
                .metodoPago(MetodoPago.PSE)
                .pedido(Pedido.builder().id(1L).build())
                .build();
    }

    List<Pago> getPagoListMock() {
        List<Pago> pagos = new ArrayList<>();
        pagos.add(Pago.builder()
                .id(1L)
                .fechaPago(LocalDateTime.of(2024, Month.MARCH,29,12,0))
                .totalPago(2000d)
                .metodoPago(MetodoPago.PSE)
                .build()
        );
        pagos.add(Pago.builder()
                .id(2L)
                .fechaPago(LocalDateTime.of(2024,Month.FEBRUARY,25,12,0))
                .totalPago(1500d)
                .metodoPago(MetodoPago.NEQUI)
                .build()
        );
        return pagos;
    }
    @Test
    void givenPago_whenCrearPago_thenReturnPagoDto() {
        //Given
        Pago pago = getPagoMock();
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                pago.getMetodoPago(),
                pago.getPedido().getId()
        );
        PagoDto pagoDto = new PagoDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                this.pedido.getId()
        );
        given(pagoMapper.pagoToSaveDtoToPago(any(PagoToSaveDto.class))).willReturn(pago);
        given(pagoMapper.pagoToPagoDto(any(Pago.class))).willReturn(pagoDto);
        given(pagoRepository.save(any(Pago.class))).willReturn(pago);
        given(pedidoRepository.findById(1L)).willReturn(Optional.of(this.pedido));
        //When
        PagoDto createdPago = pagoService.crearPago(pagoToSaveDto);
        //Then
        Assertions.assertNotNull(createdPago);
        Assertions.assertEquals(createdPago.id(), pago.getId());
    }

    @Test
    void givenPago_whenUpdate_returnUpdatedPagoDto() {
        Long pagoId = 1L;
        Pago pagoMock = getPagoMock();
        Pago updatedPago = Pago.builder()
                .id(pagoId)
                .fechaPago(LocalDateTime.of(2023, Month.DECEMBER,29,12,0))
                .totalPago(3000d)
                .metodoPago(MetodoPago.DAVIPLATA)
                .pedido(pagoMock.getPedido())
                .build();
        PagoDto pagoDto = new PagoDto(
                updatedPago.getId(),
                updatedPago.getTotalPago(),
                updatedPago.getFechaPago(),
                updatedPago.getMetodoPago(),
                updatedPago.getPedido().getId()
        );
        PagoUpdateDto pagoUpdateDto = new PagoUpdateDto(
               MetodoPago.NEQUI
        );
        given(pagoRepository.findById(pagoId)).willReturn(Optional.of(pagoMock));
        given(pagoRepository.save(any(Pago.class))).willReturn(updatedPago);
        given(pagoMapper.pagoToPagoDto(any(Pago.class))).willReturn(pagoDto);
        PagoDto updatedPagoInService = pagoService.actualizarPago(pagoId, pagoUpdateDto);
        Assertions.assertNotNull(updatedPagoInService);
        Assertions.assertEquals(updatedPagoInService.id(), pagoId);
    }

    @Test
    void givenId_whenFindById_returnPagoDto() {
        Long pagoId = 1L;
        Pago pagoMock = getPagoMock();
        PagoDto pagoDto = new PagoDto(
                pagoMock.getId(),
                pagoMock.getTotalPago(),
                pagoMock.getFechaPago(),
                pagoMock.getMetodoPago(),
                pagoMock.getPedido().getId()
        );
        given(pagoRepository.findById(pagoId)).willReturn(Optional.of(pagoMock));
        given(pagoMapper.pagoToPagoDto(any(Pago.class))).willReturn(pagoDto);
        PagoDto foundPago = pagoService.buscarPagoById(pagoId);
        Assertions.assertNotNull(foundPago);
        Assertions.assertEquals(pagoId, foundPago.id());
    }

    @Test
    void whenFindByAll_returnPagoDtoList() {
        List<Pago> pagos = getPagoListMock();
        List<PagoDto> pagoDtos = pagos.stream().map(pago -> new PagoDto(
                pago.getId(),
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                this.pedido.getId()
        )).toList();
        given(pagoRepository.findAll()).willReturn(pagos);
        given(pagoMapper.pagoListToPagoDtoList(any())).willReturn(pagoDtos);
        List<PagoDto> pagosDtosService = pagoService.getAllPagos();
        Assertions.assertNotNull(pagosDtosService);
        Assertions.assertEquals(pagosDtosService.size(), 2);
    }

    @Test
    void givenId_whenDelete_DeletePago() {
        Long pagoId = 1L;
        Pago pago = getPagoMock();
        given(pagoRepository.findById(pagoId)).willReturn(Optional.of(pago));
        doNothing().when(pagoRepository).delete(pago);
        pagoService.removerPago(pagoId);
        Assertions.assertAll(() -> pagoService.removerPago(pagoId));
    }
}
