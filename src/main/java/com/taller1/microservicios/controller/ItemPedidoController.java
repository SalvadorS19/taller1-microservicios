package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.itemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoUpdateDto;
import com.taller1.microservicios.service.ItemPedido.ItemPedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    ItemPedidoDto crearItemPedido (@RequestBody ItemPedidoToSaveDto itemPedidoToSaveDto){
        return this.itemPedidoService.crearItemPedido(itemPedidoToSaveDto);
    }

    @PutMapping("/{id}")
    ItemPedidoDto actualizarItemPedido(@PathVariable Long id,@RequestBody ItemPedidoUpdateDto itemPedidoUpdateDto){
        return this.itemPedidoService.actualizarItemPedido(id,itemPedidoUpdateDto);
    }

    @GetMapping("/{id}")
    ItemPedidoDto buscarItemPedidoById(@PathVariable Long id){
        return this.itemPedidoService.buscarItemPedidoById(id);
    }

    @DeleteMapping("/{id}")
    void removerItemPedido(@PathVariable Long id){
        this.itemPedidoService.removerItemPedido(id);
    }

    @GetMapping
    List<ItemPedidoDto> getAllItemPedidos(){
        return this.itemPedidoService.getAllItemPedidos();
    }

    @GetMapping("/order/{orderId}")
    List<ItemPedidoDto> buscarItemPedidoByidPedido(@PathVariable Long orderId){
        return this.itemPedidoService.buscarItemPedidoByidPedido(orderId);
    }

    @GetMapping("/product/{productId}")
    List<ItemPedidoDto> buscarItemPedidoByidProducto(@PathVariable Long productId){
        return this.itemPedidoService.buscarItemPedidoByidProducto(productId);
    }

    @GetMapping("/product/totalVentas/{productId}")
    Double sumaTotalVentasDeidProducto(@PathVariable Long productId){
        return this.itemPedidoService.sumaTotalVentasDeidProducto(productId);
    }
}
