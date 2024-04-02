package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.Producto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
    }

    Producto getProductoMock() {
        return Producto.builder()
                .nombre("Televisor")
                .precio(1500d)
                .stock(10)
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
    @DisplayName("Guardar un producto")
    void givenProducto_saveProducto_thenReturnSavedProducto() {
        //Given
        Producto producto = getProductoMock();
        //When
        Producto productoSaved = productoRepository.save(producto);
        //Then
        Assertions.assertNotNull(productoSaved);
        Assertions.assertTrue(productoSaved.getId() > 0);
        Assertions.assertEquals(productoSaved.getNombre(), producto.getNombre());
        Assertions.assertEquals(productoSaved.getPrecio(), producto.getPrecio());
        Assertions.assertEquals(productoSaved.getStock(), producto.getStock());
    }

    @Test
    @DisplayName("Buscar todos los productos")
    void givenProductoList_whenFindAll_thenProductoList() {
        //Given
        List<Producto> productos = getProductoListMock();
        productoRepository.saveAll(productos);
        //When
        List<Producto> foundProductos = productoRepository.findAll();
        //Then
        Assertions.assertNotNull(foundProductos);
        Assertions.assertEquals(foundProductos.size(), 2);
    }

    @Test
    @DisplayName("Buscar producto por id")
    void givenProducto_whenFindById_thenProducto() {
        //Given
        Producto productoSaved = productoRepository.save(getProductoMock());
        //When
        Optional<Producto> foundProducto = productoRepository.findById(productoSaved.getId());
        //Then
        Assertions.assertTrue(foundProducto.isPresent());
        Assertions.assertEquals(foundProducto.get().getId(), productoSaved.getId());
    }

    @Test
    @DisplayName("Eliminar un producto")
    void givenProducto_whenDelete_thenRemoveProducto() {
        //Given
        Producto productoSaved = productoRepository.save(getProductoMock());
        //When
        productoRepository.delete(productoSaved);
        Optional<Producto> removedProducto = productoRepository.findById(productoSaved.getId());
        //Then
        Assertions.assertFalse(removedProducto.isPresent());
    }

    @Test
    @DisplayName("Actualizar un producto")
    void givenProducto_whenUpdate_thenProducto() {
        //Given
        Producto productoSaved = productoRepository.save(getProductoMock());
        //When
        String nuevoNombre = "Nevera Inverter";
        Double nuevoPrecio = 3000d;
        Integer nuevoStock = 20;
        productoSaved.setNombre(nuevoNombre);
        productoSaved.setPrecio(nuevoPrecio);
        productoSaved.setStock(nuevoStock);
        Producto productoUpdated = productoRepository.save(productoSaved);
        //Then
        Assertions.assertNotNull(productoUpdated);
        Assertions.assertEquals(productoSaved.getId(), productoUpdated.getId());
        Assertions.assertEquals(productoUpdated.getNombre(), nuevoNombre);
        Assertions.assertEquals(productoUpdated.getPrecio(), nuevoPrecio);
        Assertions.assertEquals(productoUpdated.getStock(), nuevoStock);
    }

    @Test
    @DisplayName("Buscar por nombre que contenga la palabra proporcionada")
    void givenNombre_whenFindContainingNombre_thenProducto() {
        //Given
        List<Producto> productos = productoRepository.saveAll(getProductoListMock());
        String palabra = "tele";
        //When
        List<Producto> foundProductos = productoRepository.findByNombreContainingIgnoreCase(palabra);
        //Then
        Assertions.assertFalse(foundProductos.isEmpty());
        for(Producto producto : foundProductos) {
            Assertions.assertTrue(producto.getNombre().toLowerCase().contains(palabra));
        }
    }

    @Test
    @DisplayName("Buscar los productos que tengan stock mayor a 0")
    void givenProductoList_whenFindInStock_thenProductoList() {
        //Given
        List<Producto> productos = getProductoListMock();
        productos.add(Producto.builder()
                .nombre("Mesa")
                .precio(200d)
                .stock(0)
                .build()
        );
        productoRepository.saveAll(productos);
        //When
        List<Producto> foundProductos = productoRepository.findByInStock();
        //Then
        Assertions.assertFalse(foundProductos.isEmpty());
        for(Producto producto : foundProductos) {
            Assertions.assertTrue(producto.getStock() > 0);
        }
    }

    @Test
    @DisplayName("Buscar los productos que tengan stock y precio menor al proporcionado")
    void givenPrecioAndStock_whenFindProductoLessThanEqual_thenProductoList() {
        //Given
        List<Producto> productos = productoRepository.saveAll(getProductoListMock());
        Double precioMaximo = 1300d;
        Integer stockMaximo = 6;
        //When
        List<Producto> foundProductos = productoRepository
                .findByPrecioLessThanEqualAndStockLessThanEqual(precioMaximo, stockMaximo);
        //Then
        Assertions.assertFalse(foundProductos.isEmpty());
        for(Producto producto : foundProductos) {
            Assertions.assertTrue(producto.getStock() <= stockMaximo);
            Assertions.assertTrue(producto.getPrecio() <= precioMaximo);
        }
    }



}
