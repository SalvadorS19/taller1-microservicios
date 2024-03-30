package com.taller1.microservicios.repository;

import com.taller1.microservicios.AbstractIntegrationDBTest;
import com.taller1.microservicios.model.ItemPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemPedidoRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @BeforeEach
    void setUp() {
        itemPedidoRepository.deleteAll();
    }

    ItemPedido getItemPedidoMock() {
        return ItemPedido.builder()
                .cantidad(10)
                .precioUnitario(2000d)
                .build();
    }
    List<ItemPedido> getItemPedidoListMock() {
        List<ItemPedido> itemPedidos = new ArrayList<>();
        itemPedidos.add(ItemPedido.builder()
                .cantidad(10)
                .precioUnitario(2000d)
                .build()
        );
        itemPedidos.add(ItemPedido.builder()
                .cantidad(10)
                .precioUnitario(2000d)
                .build()
        );
        return itemPedidos;
    }

    @Test
    @DisplayName("Guardar un itemPedido")
    void givenItemPedido_saveItemPedido_thenReturnSavedItemPedido() {
        //Given
        ItemPedido itemPedido = getItemPedidoMock();
        //When
        ItemPedido itemPedidoSaved = itemPedidoRepository.save(itemPedido);
        //Then
        Assertions.assertNotNull(itemPedidoSaved);
        Assertions.assertTrue(itemPedidoSaved.getId() > 0);
        Assertions.assertEquals(itemPedidoSaved.getCantidad(), itemPedido.getCantidad());
        Assertions.assertEquals(itemPedidoSaved.getPrecioUnitario(), itemPedido.getPrecioUnitario());
    }

    @Test
    @DisplayName("Buscar todos los itemPedidos")
    void givenItemPedidoList_whenFindAll_thenItemPedidoList() {
        //Given
        List<ItemPedido> itemPedidos = getItemPedidoListMock();
        itemPedidoRepository.saveAll(itemPedidos);
        //When
        List<ItemPedido> foundItemPedidos = itemPedidoRepository.findAll();
        //Then
        Assertions.assertNotNull(foundItemPedidos);
        Assertions.assertEquals(foundItemPedidos.size(), 2);
    }

    @Test
    @DisplayName("Buscar itemPedido por id")
    void givenItemPedido_whenFindById_thenItemPedido() {
        //Given
        ItemPedido itemPedidoSaved = itemPedidoRepository.save(getItemPedidoMock());
        //When
        Optional<ItemPedido> foundItemPedido = itemPedidoRepository.findById(itemPedidoSaved.getId());
        //Then
        Assertions.assertTrue(foundItemPedido.isPresent());
        Assertions.assertEquals(foundItemPedido.get().getId(), itemPedidoSaved.getId());
    }

    @Test
    @DisplayName("Eliminar un itemPedido")
    void givenItemPedido_whenDelete_thenRemoveItemPedido() {
        //Given
        ItemPedido itemPedidoSaved = itemPedidoRepository.save(getItemPedidoMock());
        //When
        itemPedidoRepository.delete(itemPedidoSaved);
        Optional<ItemPedido> removedItemPedido = itemPedidoRepository.findById(itemPedidoSaved.getId());
        //Then
        Assertions.assertFalse(removedItemPedido.isPresent());
    }

    @Test
    @DisplayName("Actualizar un itemPedido")
    void givenItemPedido_whenUpdate_thenItemPedido() {
        //Given
        ItemPedido itemPedidoSaved = itemPedidoRepository.save(getItemPedidoMock());
        //When
        Integer nuevaCantidad = 2;
        Double nuevoPrecioUnitario = 3000d;
        itemPedidoSaved.setCantidad(nuevaCantidad);
        itemPedidoSaved.setPrecioUnitario(nuevoPrecioUnitario);
        ItemPedido itemPedidoUpdated = itemPedidoRepository.save(itemPedidoSaved);
        //Then
        Assertions.assertNotNull(itemPedidoUpdated);
        Assertions.assertEquals(itemPedidoSaved.getId(), itemPedidoUpdated.getId());
        Assertions.assertEquals(itemPedidoUpdated.getCantidad(), nuevaCantidad);
        Assertions.assertEquals(itemPedidoUpdated.getPrecioUnitario(), nuevoPrecioUnitario);
    }

    // TOCA HACER LOS PERSONALIZADOS
}
