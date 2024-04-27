package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.producto.ProductoDto;
import com.taller1.microservicios.dto.producto.ProductoToSaveDto;
import com.taller1.microservicios.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Crear un producto nuevo")
    @PostMapping
    ProductoDto crearProducto(@RequestBody ProductoToSaveDto productoToSaveDto){
        return this.productoService.crearProducto(productoToSaveDto);
    }

    @Operation(summary = "Actualizar un producto")
    @PutMapping("/{id}")
    ProductoDto actualizarProducto(@PathVariable Long id,@RequestBody ProductoToSaveDto productoToSaveDto){
        return this.productoService.actualizarProducto(id,productoToSaveDto);
    }

    @Operation(summary = "Buscar un producto por id")
    @GetMapping("/{id}")
    ProductoDto buscarProductoById(@PathVariable Long id){
        return this.productoService.buscarProductoById(id);
    }

    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    void removerProducto(@PathVariable Long id){
        this.productoService.removerProducto(id);
    }

    @Operation(summary = "Obtener todos los productos")
    @GetMapping
    List<ProductoDto> getAllProductos(){
        return this.productoService.getAllProductos();
    }

    @Operation(summary = "Buscar productos por termino")
    @GetMapping("/search")
    List<ProductoDto> buscarProductoByTermino(@RequestParam String searchTerm){
        return  this.productoService.buscarProductoByTermino(searchTerm);
    }

    @Operation(summary = "Buscar productos en stock")
    @GetMapping("/instock")
    List<ProductoDto> buscarProductosEnStock(){
        return  this.productoService.buscarProductosEnStock();
    }

    @Operation(summary = "Buscar productos con precio y stock menor al ingresado")
    @GetMapping("/filterLessThan")
    List<ProductoDto> buscarProductoMenoresByPrecioAndStock(@RequestParam Double precio, @RequestParam Integer stock){
        return this.productoService.buscarProductoMenoresByPrecioAndStock(precio,stock);
    }
}
