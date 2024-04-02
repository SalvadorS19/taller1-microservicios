package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.itemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoMapper;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoUpdateDto;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.repository.ItemPedidoRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import com.taller1.microservicios.repository.ProductoRepository;
import com.taller1.microservicios.service.ItemPedido.ItemPedidoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;
    @Mock
    private ItemPedidoMapper itemPedidoMapper;

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    private Pedido pedido;
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id(1L)
                .build();
        pedido = Pedido.builder()
                .id(1L)
                .build();
    }

    ItemPedido getItemPedidoMock() {
        return ItemPedido.builder()
                .id(1L)
                .cantidad(10)
                .precioUnitario(2000d)
                .pedido(this.pedido)
                .producto(this.producto)
                .build();
    }
    List<ItemPedido> getItemPedidoListMock() {
        List<ItemPedido> itemPedidos = new ArrayList<>();
        itemPedidos.add(ItemPedido.builder()
                .id(1L)
                .cantidad(10)
                .precioUnitario(2000d)
                .pedido(this.pedido)
                .producto(this.producto)
                .build()
        );
        itemPedidos.add(ItemPedido.builder()
                .id(2L)
                .cantidad(10)
                .precioUnitario(2000d)
                .pedido(this.pedido)
                .producto(this.producto)
                .build()
        );
        return itemPedidos;
    }

    @Test
    void givenItemPedido_whenCrearItemPedido_thenReturnItemPedidoDto() {
        //Given
        ItemPedido itemPedido = getItemPedidoMock();
        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                itemPedido.getCantidad(),
                this.pedido.getId(),
                this.producto.getId()
        );
        ItemPedidoDto itemPedidoDto = new ItemPedidoDto(
                itemPedido.getId(),
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario(),
                this.pedido.getId(),
                this.producto.getId()
        );
        given(itemPedidoMapper.ItemPedidoToSaveDtoToItemPedido(any(ItemPedidoToSaveDto.class))).willReturn(itemPedido);
        given(itemPedidoMapper.ItemPedidoToItemPedidoDto(any(ItemPedido.class))).willReturn(itemPedidoDto);
        given(itemPedidoRepository.save(any(ItemPedido.class))).willReturn(itemPedido);
        given(pedidoRepository.findById(any())).willReturn(Optional.of(this.pedido));
        given(productoRepository.findById(any())).willReturn(Optional.of(this.producto));
        //When
        ItemPedidoDto createdItemPedido = itemPedidoService.crearItemPedido(itemPedidoToSaveDto);
        //Then
        Assertions.assertNotNull(createdItemPedido);
        Assertions.assertEquals(createdItemPedido.id(), itemPedido.getId());
    }

    @Test
    void givenItemPedido_whenUpdate_returnUpdatedItemPedidoDto() {
        Long itemPedidoId = 1L;
        ItemPedido itemPedidoMock = getItemPedidoMock();
        ItemPedido updatedItemPedido = ItemPedido.builder()
                .id(itemPedidoId)
                .cantidad(10)
                .precioUnitario(itemPedidoMock.getPrecioUnitario())
                .pedido(this.pedido)
                .producto(this.producto)
                .build();
        ItemPedidoDto itemPedidoDto = new ItemPedidoDto(
                updatedItemPedido.getId(),
                updatedItemPedido.getCantidad(),
                updatedItemPedido.getPrecioUnitario(),
                this.pedido.getId(),
                this.producto.getId()
        );
        ItemPedidoUpdateDto itemPedidoToUpdate = new ItemPedidoUpdateDto(
                updatedItemPedido.getCantidad()
        );
        given(itemPedidoRepository.findById(itemPedidoId)).willReturn(Optional.of(itemPedidoMock));
        given(itemPedidoRepository.save(any(ItemPedido.class))).willReturn(updatedItemPedido);
        given(itemPedidoMapper.ItemPedidoToItemPedidoDto(any(ItemPedido.class))).willReturn(itemPedidoDto);
        ItemPedidoDto updatedItemPedidoInService = itemPedidoService.actualizarItemPedido(itemPedidoId, itemPedidoToUpdate);
        Assertions.assertNotNull(updatedItemPedidoInService);
        Assertions.assertEquals(updatedItemPedidoInService.id(), itemPedidoId);
    }

    @Test
    void givenId_whenFindById_returnItemPedidoDto() {
        Long itemPedidoId = 1L;
        ItemPedido itemPedidoMock = getItemPedidoMock();
        ItemPedidoDto itemPedidoDto = new ItemPedidoDto(
                itemPedidoMock.getId(),
                itemPedidoMock.getCantidad(),
                itemPedidoMock.getPrecioUnitario(),
                this.pedido.getId(),
                this.producto.getId()
        );
        given(itemPedidoRepository.findById(itemPedidoId)).willReturn(Optional.of(itemPedidoMock));
        given(itemPedidoMapper.ItemPedidoToItemPedidoDto(any(ItemPedido.class))).willReturn(itemPedidoDto);
        ItemPedidoDto foundItemPedido = itemPedidoService.buscarItemPedidoById(itemPedidoId);
        Assertions.assertNotNull(foundItemPedido);
        Assertions.assertEquals(itemPedidoId, foundItemPedido.id());
    }

    @Test
    void whenFindByAll_returnItemPedidoDtoList() {
        List<ItemPedido> itemPedidos = getItemPedidoListMock();
        List<ItemPedidoDto> itemPedidoDtos = itemPedidos.stream().map(itemPedido -> new ItemPedidoDto(
                itemPedido.getId(),
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario(),
                this.pedido.getId(),
                this.producto.getId()
        )).toList();
        given(itemPedidoRepository.findAll()).willReturn(itemPedidos);
        given(itemPedidoMapper.itemPedidoListToItemPedidoDtoList(any())).willReturn(itemPedidoDtos);
        List<ItemPedidoDto> itemPedidosDtosService = itemPedidoService.getAllItemPedidos();
        Assertions.assertFalse(itemPedidosDtosService.isEmpty());
        Assertions.assertEquals(itemPedidosDtosService.size(), 2);
    }

    @Test
    void givenId_whenDelete_DeleteItemPedido() {
        Long itemPedidoId = 1L;
        ItemPedido itemPedido = getItemPedidoMock();
        given(itemPedidoRepository.findById(itemPedidoId)).willReturn(Optional.of(itemPedido));
        doNothing().when(itemPedidoRepository).delete(itemPedido);
        itemPedidoService.removerItemPedido(itemPedidoId);
        Assertions.assertAll(() -> itemPedidoService.removerItemPedido(itemPedidoId));
    }

    @Test
    void whenFindByPedidoId_returnItemPedidoDtoList() {
        Long pedidoId = 1L;
        List<ItemPedido> itemsPedidosMock = getItemPedidoListMock();
        List<ItemPedidoDto> itemPedidoDtos = itemsPedidosMock.stream().map(itemPedido ->
                new ItemPedidoDto(
                        itemPedido.getId(),
                        itemPedido.getCantidad(),
                        itemPedido.getPrecioUnitario(),
                        this.pedido.getId(),
                        this.producto.getId()
                )
        ).toList();
        given(itemPedidoRepository.findByPedidoId(pedidoId)).willReturn(itemsPedidosMock);
        given(itemPedidoMapper.itemPedidoListToItemPedidoDtoList(any())).willReturn(itemPedidoDtos);
        List<ItemPedidoDto> foundItemPedido = itemPedidoService.buscarItemPedidoByidPedido(pedidoId);
        Assertions.assertFalse(foundItemPedido.isEmpty());
        for (ItemPedidoDto itemPedidoDto : foundItemPedido) {
            Assertions.assertEquals(itemPedidoDto.pedidoId(), pedidoId);
        }
    }

    @Test
    void whenFindByProductoId_returnItemPedidoDtoList() {
        Long productoId = 1L;
        List<ItemPedido> itemsPedidosMock = getItemPedidoListMock();
        List<ItemPedidoDto> itemPedidoDtos = itemsPedidosMock.stream().map(itemPedido ->
                new ItemPedidoDto(
                        itemPedido.getId(),
                        itemPedido.getCantidad(),
                        itemPedido.getPrecioUnitario(),
                        this.pedido.getId(),
                        this.producto.getId()
                )
        ).toList();
        given(itemPedidoRepository.findByProductoId(productoId)).willReturn(itemsPedidosMock);
        given(itemPedidoMapper.itemPedidoListToItemPedidoDtoList(any())).willReturn(itemPedidoDtos);
        List<ItemPedidoDto> foundItemPedido = itemPedidoService.buscarItemPedidoByidProducto(productoId);
        Assertions.assertFalse(foundItemPedido.isEmpty());
        for (ItemPedidoDto itemPedidoDto : foundItemPedido) {
            Assertions.assertEquals(itemPedidoDto.productoId(), productoId);
        }
    }

    @Test
    void givenProductoId_whenSumaTotalVentas_returnTotalVentas() {
        Long productoId = 1L;
        List<ItemPedido> itemsPedidosMock = getItemPedidoListMock();
        Double sumaTotal = 0d;
        for(ItemPedido itemPedido : itemsPedidosMock) {
            if (Objects.equals(itemPedido.getProducto().getId(), productoId)) {
                sumaTotal += itemPedido.getCantidad() * itemPedido.getPrecioUnitario();
            }
        }
        given(itemPedidoRepository.findTotalVentasByProducto(productoId)).willReturn(Optional.of(sumaTotal));
        Double sumaTotalItems = itemPedidoService.sumaTotalVentasDeidProducto(productoId);
        Assertions.assertNotNull(sumaTotalItems);
        Assertions.assertEquals(sumaTotalItems, sumaTotal);
    }
}
