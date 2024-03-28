package com.taller1.microservicios.service.producto;

import com.taller1.microservicios.dto.Producto.ProductoDto;
import com.taller1.microservicios.dto.Producto.ProductoMapper;
import com.taller1.microservicios.dto.Producto.ProductoToSaveDto;
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
        productoRepository.save(producto);
        return this.productoMapper.productoToProductoDto(producto);
    }

    @Override
    public ProductoDto actualizarProducto(Long id,ProductoToSaveDto productoToSaveDto) {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe"));
        producto.setNombre(productoToSaveDto.nombre());
        producto.setPrecio(productoToSaveDto.precio());
        producto.setStock(productoToSaveDto.stock());

        productoRepository.save(producto);
        return this.productoMapper.productoToProductoDto(producto);
    }

    @Override
    public ProductoDto buscarProductoById(Long id) {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe"));
        return this.productoMapper.productoToProductoDto(producto);
    }

    @Override
    public void removerProducto(Long id) {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe"));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductoDto> getAllProductos() {
        List<Producto> productos = this.productoRepository.findAll();
        return this.productoMapper.productosListToProductosDtoList(productos);    }

    @Override
    public List<ProductoDto> buscarProductoByTermino(String termino) {
        List<Producto> productos= this.productoRepository.findByNombreContaining(termino);
        return this.productoMapper.productosListToProductosDtoList(productos);    }

    @Override
    public List<ProductoDto> buscarProductosByStock() {
        List<Producto> productos= this.productoRepository.findByInStock();
        return this.productoMapper.productosListToProductosDtoList(productos);    }

    @Override
    public List<ProductoDto> buscarProductoMenoresByPrecioAndStock(Double precio, Integer Stock) {
        List<Producto> productos= this.productoRepository.findByPrecioLessThanEqualAndStockLessThanEqual(precio,Stock);
        return this.productoMapper.productosListToProductosDtoList(productos);
    }

}
