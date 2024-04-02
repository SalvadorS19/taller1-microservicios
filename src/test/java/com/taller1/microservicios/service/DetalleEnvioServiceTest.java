package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioMapper;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioUpdateDto;
import com.taller1.microservicios.model.DetalleEnvio;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoEnvio;
import com.taller1.microservicios.repository.DetalleEnvioRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import com.taller1.microservicios.service.detalleEnvio.DetalleEnvioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class DetalleEnvioServiceTest {

    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;
    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private DetalleEnvioMapper detalleEnvioMapper;

    @InjectMocks
    private DetalleEnvioServiceImpl detalleEnvioService;

    private List<ItemPedido> itemsPedido;

    private Pedido pedido;

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

    DetalleEnvio getDetalleEnvioMock() {
        return DetalleEnvio.builder()
                .id(1L)
                .direccion("Calle 123")
                .transportadora("Deprisa")
                .estadoEnvio(EstadoEnvio.ENVIADO)
                .numeroGuia("ENV-2024312")
                .pedido(this.pedido)
                .build();
    }
    List<DetalleEnvio> getDetalleEnvioListMock() {
        List<DetalleEnvio> detalleEnvios = new ArrayList<>();
        detalleEnvios.add(DetalleEnvio.builder()
                .id(1L)
                .direccion("Calle 123")
                .transportadora("Deprisa")
                .estadoEnvio(EstadoEnvio.ENVIADO)
                .numeroGuia("ENV-2024312")
                .build()
        );
        detalleEnvios.add(DetalleEnvio.builder()
                .id(2L)
                .direccion("Calle ABC")
                .transportadora("Pasarex")
                .estadoEnvio(EstadoEnvio.EN_REPARTO)
                .numeroGuia("ENV-20243542")
                .build()
        );
        return detalleEnvios;
    }

    @Test
    void givenDetalleEnvio_whenCrearDetalleEnvio_thenReturnDetalleEnvioDto() {
        //Given
        DetalleEnvio detalleEnvio = getDetalleEnvioMock();
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                pedido.getId()
        );
        DetalleEnvioDto detalleEnvioDto = new DetalleEnvioDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getEstadoEnvio(),
                detalleEnvio.getNumeroGuia(),
                this.pedido.getId()
        );
        given(detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvio(any(DetalleEnvioToSaveDto.class))).willReturn(detalleEnvio);
        given(detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(any(DetalleEnvio.class))).willReturn(detalleEnvioDto);
        given(detalleEnvioRepository.save(any(DetalleEnvio.class))).willReturn(detalleEnvio);
        given(pedidoRepository.findById(1L)).willReturn(Optional.of(this.pedido));
        //When
        DetalleEnvioDto createdDetalleEnvio = detalleEnvioService.crearDetalleEnvio(detalleEnvioToSaveDto);
        //Then
        Assertions.assertNotNull(createdDetalleEnvio);
        Assertions.assertEquals(createdDetalleEnvio.id(), detalleEnvio.getId());
    }

    @Test
    void givenDetalleEnvio_whenUpdate_returnUpdatedDetalleEnvioDto() {
        Long detalleEnvioId = 1L;
        DetalleEnvio detalleEnvioMock = getDetalleEnvioMock();
        DetalleEnvio updatedDetalleEnvio = DetalleEnvio.builder()
                .id(detalleEnvioId)
                .direccion("Calle 15B")
                .transportadora("Envia")
                .estadoEnvio(EstadoEnvio.ENVIADO)
                .numeroGuia("ENV-2024312")
                .build();
        DetalleEnvioDto detalleEnvioDto = new DetalleEnvioDto(
                updatedDetalleEnvio.getId(),
                updatedDetalleEnvio.getDireccion(),
                updatedDetalleEnvio.getTransportadora(),
                updatedDetalleEnvio.getEstadoEnvio(),
                updatedDetalleEnvio.getNumeroGuia(),
                this.pedido.getId()
        );
        DetalleEnvioUpdateDto detalleEnvioUpdateDto = new DetalleEnvioUpdateDto(
                updatedDetalleEnvio.getDireccion(),
                updatedDetalleEnvio.getTransportadora(),
                updatedDetalleEnvio.getEstadoEnvio()
        );
        given(detalleEnvioRepository.findById(detalleEnvioId)).willReturn(Optional.of(detalleEnvioMock));
        given(detalleEnvioRepository.save(any(DetalleEnvio.class))).willReturn(updatedDetalleEnvio);
        given(detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(any(DetalleEnvio.class))).willReturn(detalleEnvioDto);
        DetalleEnvioDto updatedDetalleEnvioInService = detalleEnvioService.actualizarDetalleEnvio(detalleEnvioId, detalleEnvioUpdateDto);
        Assertions.assertNotNull(updatedDetalleEnvioInService);
        Assertions.assertEquals(updatedDetalleEnvioInService.id(), detalleEnvioId);
    }

    @Test
    void givenId_whenFindById_returnDetalleEnvioDto() {
        Long detalleEnvioId = 1L;
        DetalleEnvio detalleEnvioMock = getDetalleEnvioMock();
        DetalleEnvioDto detalleEnvioDto = new DetalleEnvioDto(
                detalleEnvioMock.getId(),
                detalleEnvioMock.getDireccion(),
                detalleEnvioMock.getTransportadora(),
                detalleEnvioMock.getEstadoEnvio(),
                detalleEnvioMock.getNumeroGuia(),
                this.pedido.getId()
        );
        given(detalleEnvioRepository.findById(detalleEnvioId)).willReturn(Optional.of(detalleEnvioMock));
        given(detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(any(DetalleEnvio.class))).willReturn(detalleEnvioDto);
        DetalleEnvioDto foundDetalleEnvio = detalleEnvioService.buscarDetalleEnvioById(detalleEnvioId);
        Assertions.assertNotNull(foundDetalleEnvio);
        Assertions.assertEquals(detalleEnvioId, foundDetalleEnvio.id());
    }

    @Test
    void whenFindByAll_returnDetalleEnvioDtoList() {
        List<DetalleEnvio> detalleEnvios = getDetalleEnvioListMock();
        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvios.stream().map(detalleEnvio -> new DetalleEnvioDto(
                detalleEnvio.getId(),
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getEstadoEnvio(),
                detalleEnvio.getNumeroGuia(),
                this.pedido.getId()
        )).toList();
        given(detalleEnvioRepository.findAll()).willReturn(detalleEnvios);
        given(detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(any())).willReturn(detalleEnvioDtos);
        List<DetalleEnvioDto> detalleEnviosDtosService = detalleEnvioService.getAllDetalleEnvio();
        Assertions.assertFalse(detalleEnviosDtosService.isEmpty());
        Assertions.assertEquals(detalleEnviosDtosService.size(), 2);
    }

    @Test
    void givenId_whenDelete_DeleteDetalleEnvio() {
        Long detalleEnvioId = 1L;
        DetalleEnvio detalleEnvio = getDetalleEnvioMock();
        given(detalleEnvioRepository.findById(detalleEnvioId)).willReturn(Optional.of(detalleEnvio));
        doNothing().when(detalleEnvioRepository).delete(detalleEnvio);
        detalleEnvioService.removerDetalleEnvio(detalleEnvioId);
        Assertions.assertAll(() -> detalleEnvioService.removerDetalleEnvio(detalleEnvioId));
    }
    @Test
    void whenFindByPedidoId_returnDetalleEnvioDto() {
        Long pedidoId = 1L;
        DetalleEnvio detalleEnvioMock = getDetalleEnvioMock();
        DetalleEnvioDto detalleEnvioDto = new DetalleEnvioDto(
                detalleEnvioMock.getId(),
                detalleEnvioMock.getDireccion(),
                detalleEnvioMock.getTransportadora(),
                detalleEnvioMock.getEstadoEnvio(),
                detalleEnvioMock.getNumeroGuia(),
                this.pedido.getId()
        );
        given(detalleEnvioRepository.findById(pedidoId)).willReturn(Optional.of(detalleEnvioMock));
        given(detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(any(DetalleEnvio.class))).willReturn(detalleEnvioDto);
        DetalleEnvioDto foundDetalleEnvio = detalleEnvioService.buscarDetalleEnvioById(pedidoId);
        Assertions.assertNotNull(foundDetalleEnvio);
        Assertions.assertEquals(foundDetalleEnvio.pedidoId(), pedidoId);
    }
    @Test
    void whenFindByTransportadora_returnDetalleEnvioDtoList() {
        String transportadora = "Deprisa";
        List<DetalleEnvio> detalleEnvios = getDetalleEnvioListMock();
        List<DetalleEnvioDto> detalleEnvioDtos = new ArrayList<>();
        for (DetalleEnvio detalleEnvio : detalleEnvios) {
            if (detalleEnvio.getTransportadora().equals(transportadora)) {
                detalleEnvioDtos.add(new DetalleEnvioDto(
                        detalleEnvio.getId(),
                        detalleEnvio.getDireccion(),
                        detalleEnvio.getTransportadora(),
                        detalleEnvio.getEstadoEnvio(),
                        detalleEnvio.getNumeroGuia(),
                        this.pedido.getId()
                ));
            }
        }
        given(detalleEnvioRepository.findByTransportadora(transportadora)).willReturn(detalleEnvios);
        given(detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(any())).willReturn(detalleEnvioDtos);
        List<DetalleEnvioDto> foundDetalleEnvios = detalleEnvioService.getDetalleEnviosByTransportadora(transportadora);
        Assertions.assertFalse(foundDetalleEnvios.isEmpty());
        for (DetalleEnvioDto detalleEnvioDto : foundDetalleEnvios) {
            Assertions.assertEquals(detalleEnvioDto.transportadora(), transportadora);
        }
    }

    @Test
    void whenFindByEstado_returnDetalleEnvioDtoList() {
        EstadoEnvio estadoEnvio = EstadoEnvio.ENVIADO;
        List<DetalleEnvio> detalleEnvios = getDetalleEnvioListMock();
        List<DetalleEnvioDto> detalleEnvioDtos = new ArrayList<>();
        for (DetalleEnvio detalleEnvio : detalleEnvios) {
            if (detalleEnvio.getEstadoEnvio().equals(estadoEnvio)) {
                detalleEnvioDtos.add(new DetalleEnvioDto(
                        detalleEnvio.getId(),
                        detalleEnvio.getDireccion(),
                        detalleEnvio.getTransportadora(),
                        detalleEnvio.getEstadoEnvio(),
                        detalleEnvio.getNumeroGuia(),
                        this.pedido.getId()
                ));
            }
        }
        given(detalleEnvioRepository.findByEstadoEnvio(estadoEnvio)).willReturn(detalleEnvios);
        given(detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(any())).willReturn(detalleEnvioDtos);
        List<DetalleEnvioDto> foundDetalleEnvios = detalleEnvioService.getDetalleEnviosByEstado(estadoEnvio);
        Assertions.assertFalse(foundDetalleEnvios.isEmpty());
        for (DetalleEnvioDto detalleEnvioDto : foundDetalleEnvios) {
            Assertions.assertEquals(detalleEnvioDto.estadoEnvio(), estadoEnvio);
        }
    }
}
