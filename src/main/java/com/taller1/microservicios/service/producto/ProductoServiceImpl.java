package com.taller1.microservicios.service.producto;

import com.taller1.microservicios.dto.producto.ProductoDto;
import com.taller1.microservicios.dto.producto.ProductoMapper;
import com.taller1.microservicios.dto.producto.ProductoToSaveDto;
import com.taller1.microservicios.exception.ProductoNotFoundException;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoServiceImpl implements ProductoService{

    private final ProductoMapper productoMapper;
    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoMapper productoMapper, ProductoRepository productoRepository) {
        this.productoMapper = productoMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoDto crearProducto(ProductoToSaveDto productoToSaveDto) {
        Producto producto = this.productoMapper.productoToSaveDtoToProducto(productoToSaveDto);
        producto = productoRepository.save(producto);
        return this.productoMapper.productoToProductoDto(producto);
    }

    @Override
    public ProductoDto actualizarProducto(Long id,ProductoToSaveDto productoToSaveDto) throws ProductoNotFoundException {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("El producto no existe"));
        producto.setNombre(productoToSaveDto.nombre());
        producto.setPrecio(productoToSaveDto.precio());
        producto.setStock(productoToSaveDto.stock());
        producto = productoRepository.save(producto);
        return this.productoMapper.productoToProductoDto(producto);
    }

    @Override
    public ProductoDto buscarProductoById(Long id) throws ProductoNotFoundException {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("El producto no existe"));
        return this.productoMapper.productoToProductoDto(producto);
    }

    @Override
    public void removerProducto(Long id) throws ProductoNotFoundException {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("El producto no existe"));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductoDto> getAllProductos() {
        List<Producto> productos = this.productoRepository.findAll();
        return this.productoMapper.productosListToProductosDtoList(productos);
    }

    @Override
    public List<ProductoDto> buscarProductoByTermino(String termino) {
        List<Producto> productos = this.productoRepository.findByNombreContainingIgnoreCase(termino);
        return this.productoMapper.productosListToProductosDtoList(productos);
    }

    @Override
    public List<ProductoDto> buscarProductosEnStock() {
        List<Producto> productos = this.productoRepository.findByInStock();
        return this.productoMapper.productosListToProductosDtoList(productos);
    }

    @Override
    public List<ProductoDto> buscarProductoMenoresByPrecioAndStock(Double precio, Integer stock) {
        List<Producto> productos = this.productoRepository.findByPrecioLessThanEqualAndStockLessThanEqual(precio, stock);
        return this.productoMapper.productosListToProductosDtoList(productos);
    }

}
