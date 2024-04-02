package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.producto.ProductoDto;
import com.taller1.microservicios.dto.producto.ProductoMapper;
import com.taller1.microservicios.dto.producto.ProductoToSaveDto;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.repository.ProductoRepository;

import com.taller1.microservicios.service.producto.ProductoServiceImpl;
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

@ExtendWith({MockitoExtension.class})
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private ProductoMapper productoMapper;
    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
    }

    Producto getProductoMock() {
        return Producto.builder()
                .id(1L)
                .nombre("Televisor LG")
                .stock(10)
                .precio(500d)
                .build();
    }

    List<Producto> getProductoListMock() {
        List<Producto> productos = new ArrayList<>();
        productos.add(Producto.builder()
                .nombre("Televisor")
                .precio(1500d)
                .stock(10)
                .build()
        );
        productos.add(Producto.builder()
                .nombre("Nevera")
                .precio(1200d)
                .stock(5)
                .build()
        );
        return productos;
    }

    @Test
    void givenProducto_whenCrearProducto_thenReturnProductoDto() {
        //Given
        Producto producto = getProductoMock();
        ProductoToSaveDto productoToSaveDto = new ProductoToSaveDto(
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
        );
        ProductoDto productoDto = new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
        );
        given(productoMapper.productoToSaveDtoToProducto(any(ProductoToSaveDto.class))).willReturn(producto);
        given(productoMapper.productoToProductoDto(any(Producto.class))).willReturn(productoDto);
        given(productoRepository.save(any(Producto.class))).willReturn(producto);
        //When
        ProductoDto createdProducto = productoService.crearProducto(productoToSaveDto);
        //Then
        Assertions.assertNotNull(createdProducto);
        Assertions.assertEquals(createdProducto.id(), producto.getId());
    }

    @Test
    void givenProducto_whenUpdate_returnUpdatedProductoDto() {
        Long productoId = 1L;
        Producto productoMock = getProductoMock();
        Producto updatedProducto = Producto.builder()
                .id(productoId)
                .nombre("Televisor Samsung")
                .stock(5)
                .precio(500d)
                .build();
        ProductoDto productoDto = new ProductoDto(
                updatedProducto.getId(),
                updatedProducto.getNombre(),
                updatedProducto.getPrecio(),
                updatedProducto.getStock()
        );
        ProductoToSaveDto productoToUpdate = new ProductoToSaveDto(
                updatedProducto.getNombre(),
                updatedProducto.getPrecio(),
                updatedProducto.getStock()
        );
        given(productoRepository.findById(productoId)).willReturn(Optional.of(productoMock));
        given(productoRepository.save(any(Producto.class))).willReturn(updatedProducto);
        given(productoMapper.productoToProductoDto(any(Producto.class))).willReturn(productoDto);
        ProductoDto updatedProductoInService = productoService.actualizarProducto(productoId, productoToUpdate);
        Assertions.assertNotNull(updatedProductoInService);
        Assertions.assertEquals(updatedProductoInService.id(), productoId);
    }

    @Test
    void givenId_whenFindById_returnProductoDto() {
        Long productoId = 1L;
        Producto productoMock = getProductoMock();
        ProductoDto productoDto = new ProductoDto(
                productoMock.getId(),
                productoMock.getNombre(),
                productoMock.getPrecio(),
                productoMock.getStock()
        );
        given(productoRepository.findById(productoId)).willReturn(Optional.of(productoMock));
        given(productoMapper.productoToProductoDto(any(Producto.class))).willReturn(productoDto);
        ProductoDto foundProducto = productoService.buscarProductoById(productoId);
        Assertions.assertNotNull(foundProducto);
        Assertions.assertEquals(productoId, foundProducto.id());
    }

    @Test
    void whenFindByAll_returnProductoDtoList() {
        List<Producto> productos = getProductoListMock();
        List<ProductoDto> productoDtos = productos.stream().map(producto -> new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
        )).toList();
        given(productoRepository.findAll()).willReturn(productos);
        given(productoMapper.productosListToProductosDtoList(any())).willReturn(productoDtos);
        List<ProductoDto> productosDtosService = productoService.getAllProductos();
        Assertions.assertFalse(productosDtosService.isEmpty());
        Assertions.assertEquals(productosDtosService.size(), 2);
    }

    @Test
    void givenId_whenDelete_DeleteProducto() {
        Long productoId = 1L;
        Producto producto = getProductoMock();
        given(productoRepository.findById(productoId)).willReturn(Optional.of(producto));
        doNothing().when(productoRepository).delete(producto);
        productoService.removerProducto(productoId);
        Assertions.assertAll(() -> productoService.removerProducto(productoId));
    }

    @Test
    void givenTermino_whenFindByTermino_returnProductoDtoList() {
        String palabra = "tele";
        List<Producto> productos = new ArrayList<>();
        productos.add(Producto.builder()
                .id(1L)
                .nombre("Televisor LG")
                .stock(5)
                .precio(500d)
                .build()
        );
        productos.add(Producto.builder()
                .id(2L)
                .nombre("Televisor Samsung")
                .stock(10)
                .precio(1000d)
                .build()
        );
        List<ProductoDto> productoDtoList = productos.stream().map(producto ->
                new ProductoDto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock()
                )
        ).toList();
        given(productoRepository.findByNombreContainingIgnoreCase(palabra)).willReturn(productos);
        given(productoMapper.productosListToProductosDtoList(productos)).willReturn(productoDtoList);
        List<ProductoDto> productoDtos = productoService.buscarProductoByTermino(palabra);
        Assertions.assertFalse(productoDtos.isEmpty());
        Assertions.assertEquals(productoDtos.size(), 2);
    }

    @Test
    void whenFindByInStock_returnProductoDtoList() {
        List<Producto> productos = getProductoListMock();
        List<ProductoDto> productoDtoList = productos.stream().map(producto ->
                new ProductoDto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock()
                )
        ).toList();
        given(productoRepository.findByInStock()).willReturn(productos);
        given(productoMapper.productosListToProductosDtoList(productos)).willReturn(productoDtoList);
        List<ProductoDto> productoDtos = productoService.buscarProductosEnStock();
        Assertions.assertFalse(productoDtos.isEmpty());
        Assertions.assertEquals(productoDtos.size(), 2);
    }
    @Test
    void givenPrecioAndStock_whenFindByLessEqualThan_returnProductoDtoList() {
        Integer stockMax = 10;
        Double precioMax = 2000d;
        List<Producto> productos = getProductoListMock();
        List<ProductoDto> productoDtoList = productos.stream().map(producto ->
                new ProductoDto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock()
                )
        ).toList();
        given(productoRepository.findByPrecioLessThanEqualAndStockLessThanEqual(precioMax, stockMax)).willReturn(productos);
        given(productoMapper.productosListToProductosDtoList(productos)).willReturn(productoDtoList);
        List<ProductoDto> productoDtos = productoService.buscarProductoMenoresByPrecioAndStock(precioMax, stockMax);
        Assertions.assertFalse(productoDtos.isEmpty());
        Assertions.assertEquals(productoDtos.size(), 2);
    }
}
