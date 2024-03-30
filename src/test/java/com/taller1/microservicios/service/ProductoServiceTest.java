package com.taller1.microservicios.service;

import com.taller1.microservicios.dto.producto.ProductoDto;
import com.taller1.microservicios.dto.producto.ProductoMapper;
import com.taller1.microservicios.dto.producto.ProductoMapperImpl;
import com.taller1.microservicios.dto.producto.ProductoToSaveDto;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.repository.ProductoRepository;

import com.taller1.microservicios.service.producto.ProductoService;
import com.taller1.microservicios.service.producto.ProductoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;
    @InjectMocks
    private ProductoServiceImpl productoService;
    private final ProductoMapper productoMapper = new ProductoMapperImpl();

    @BeforeEach
    void setUp() {
        productoService = new ProductoServiceImpl(productoMapper, productoRepository);
    }

    Producto getProductoMock() {
        return Producto.builder()
                .id(1L)
                .nombre("Televisor LG")
                .stock(10)
                .precio(500d)
                .build();
    }

    @Test
    void givenProducto_whenCrearProducto_thenReturnProductoDto() {
        //Given
        Producto producto = getProductoMock();
        given(productoRepository.save(any())).willReturn(producto);
        ProductoToSaveDto productoToSaveDto = new ProductoToSaveDto(
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
        );
        //When
        ProductoDto productoDto = productoService.crearProducto(productoToSaveDto);
        System.out.println(productoDto.id());
        //Then
        Assertions.assertNotNull(productoDto);
        Assertions.assertEquals(productoDto.id(), producto.getId());
    }
}
