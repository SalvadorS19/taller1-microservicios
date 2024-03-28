package com.taller1.microservicios.service.producto;
import com.taller1.microservicios.dto.Producto.ProductoDto;
import com.taller1.microservicios.dto.Producto.ProductoToSaveDto;
import java.util.List;
public interface ProductoService {

    ProductoDto crearProducto (ProductoToSaveDto productoToSaveDto);
    ProductoDto actualizarProducto(Long id, ProductoToSaveDto productoToSaveDto);
    ProductoDto buscarProductoById(Long id);
    void removerProducto(Long id);
    List<ProductoDto> getAllProductos();
    List<ProductoDto> buscarProductoByTermino(String termino);
    List<ProductoDto> buscarProductosEnStock();
    List<ProductoDto> buscarProductoMenoresByPrecioAndStock(Double precio, Integer Stock);
}
