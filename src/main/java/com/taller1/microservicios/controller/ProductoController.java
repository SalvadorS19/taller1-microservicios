package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.producto.ProductoDto;
import com.taller1.microservicios.dto.producto.ProductoToSaveDto;
import com.taller1.microservicios.service.producto.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    ProductoDto crearProducto(@RequestBody ProductoToSaveDto productoToSaveDto){
        return this.productoService.crearProducto(productoToSaveDto);
    }

    @PutMapping("/{id}")
    ProductoDto actualizarProducto(@PathVariable Long id,@RequestBody ProductoToSaveDto productoToSaveDto){
        return this.productoService.actualizarProducto(id,productoToSaveDto);
    }

    @GetMapping("/{id}")
    ProductoDto buscarProductoById(@PathVariable Long id){
        return this.productoService.buscarProductoById(id);
    }

    @DeleteMapping("/{id}")
    void removerProducto(@PathVariable Long id){
        this.productoService.removerProducto(id);
    }

    @GetMapping
    List<ProductoDto> getAllProductos(){
        return this.productoService.getAllProductos();
    }

    @GetMapping("/search")
    List<ProductoDto> buscarProductoByTermino(@RequestParam String searchTerm){
        return  this.productoService.buscarProductoByTermino(searchTerm);
    }

    @GetMapping("/instock")
    List<ProductoDto> buscarProductosEnStock(){
        return  this.productoService.buscarProductosEnStock();
    }

    @GetMapping("/filterLessThan")
    List<ProductoDto> buscarProductoMenoresByPrecioAndStock(@RequestParam Double precio, @RequestParam Integer stock){
        return this.productoService.buscarProductoMenoresByPrecioAndStock(precio,stock);
    }
}
