package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.Producto.ProductoDto;
import com.taller1.microservicios.dto.Producto.ProductoToSaveDto;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.service.producto.ProductoService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/producto")
    ProductoDto crearProducto(@RequestBody ProductoToSaveDto productoToSaveDto){
        return this.productoService.crearProducto(productoToSaveDto);
    }

    @PutMapping("/producto/{id}")
    ProductoDto actualizarProducto(@PathVariable Long id,@RequestBody ProductoToSaveDto productoToSaveDto){
        return this.productoService.actualizarProducto(id,productoToSaveDto);
    }

    @GetMapping("/producto/{id}")
    ProductoDto buscarProductoById(@PathVariable Long id){
        return this.productoService.buscarProductoById(id);
    }

    @DeleteMapping("/producto/{id}")
    void removerProducto(@PathVariable Long id){
        this.productoService.removerProducto(id);
    }

    @GetMapping("/producto")
    List<ProductoDto> getAllProductos(){
        return this.productoService.getAllProductos();
    }

    @GetMapping("/producto/porTermino/{termino}")
    List<ProductoDto> buscarProductoByTermino(@PathVariable String termino){
        return  this.productoService.buscarProductoByTermino(termino);
    }

    @GetMapping("/producto/enStock")
    List<ProductoDto> buscarProductosByStock(){
        return  this.productoService.buscarProductosByStock();
    }

    @GetMapping("/producto/filterMenorA")
    List<ProductoDto> buscarProductoMenoresByPrecioAndStock(@RequestParam Double precio, @RequestParam Integer stock){
        return this.productoService.buscarProductoMenoresByPrecioAndStock(precio,stock);
    }
}
