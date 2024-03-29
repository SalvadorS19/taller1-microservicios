package com.taller1.microservicios.dto.producto;

import com.taller1.microservicios.model.Producto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDto productoToProductoDto(Producto producto);
    Producto productoToSaveDtoToProducto(ProductoToSaveDto productoToSaveDto);
    List<ProductoDto> productosListToProductosDtoList(List<Producto> productos);
}
