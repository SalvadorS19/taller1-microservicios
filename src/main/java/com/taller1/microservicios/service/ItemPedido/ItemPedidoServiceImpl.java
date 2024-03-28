package com.taller1.microservicios.service.ItemPedido;

import com.taller1.microservicios.dto.ItemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoMapper;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoUpdateDto;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.exception.ProductoNotFoundException;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.Producto;
import com.taller1.microservicios.repository.ItemPedidoRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import com.taller1.microservicios.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{
    private final ItemPedidoMapper itemPedidoMapper;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public ItemPedidoServiceImpl(
            ItemPedidoMapper itemPedidoMapper,
            ItemPedidoRepository itemPedidoRepository,
            ProductoRepository productoRepository,
            PedidoRepository pedidoRepository
    ) {
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public ItemPedidoDto crearItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto) {
        Pedido pedido = this.pedidoRepository.findById(itemPedidoToSaveDto.pedidoId())
                .orElseThrow(() -> new PedidoNotFoundException("El pedido no existe"));
        Producto producto = this.productoRepository.findById(itemPedidoToSaveDto.productoId())
                .orElseThrow(() -> new ProductoNotFoundException("El producto no existe"));
        ItemPedido itemPedido= this.itemPedidoMapper.ItemPedidoToSaveDtoToItemPedido(itemPedidoToSaveDto);
        itemPedido.setPedido(pedido);
        itemPedido.setProducto(producto);
        itemPedido.setPrecioUnitario(producto.getPrecio());
        itemPedidoRepository.save(itemPedido);
        return this.itemPedidoMapper.ItemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoUpdateDto itemPedidoUpdateDto) {
        ItemPedido itemPedido= this.itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido no existe"));
        itemPedido.setCantidad(itemPedidoUpdateDto.cantidad());
        itemPedido.setPrecioUnitario(itemPedidoUpdateDto.precioUnitario());
        itemPedidoRepository.save(itemPedido);
        return this.itemPedidoMapper.ItemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public ItemPedidoDto buscarItemPedidoById(Long id) {
        ItemPedido itemPedido= this.itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido no existe"));
        return this.itemPedidoMapper.ItemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public void removerItemPedido(Long id) {
        ItemPedido itemPedido= this.itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido no existe"));
        itemPedidoRepository.delete(itemPedido);
    }

    @Override
    public List<ItemPedidoDto> getAllItemPedidos() {
        List<ItemPedido> pedidos = this.itemPedidoRepository.findAll();
        return this.itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedidos);
    }

    @Override
    public List<ItemPedidoDto> buscarItemPedidoByidPedido(Long idPedido) {
        List<ItemPedido> pedidos = this.itemPedidoRepository.findByPedidoId(idPedido);
        return this.itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedidos);
    }

    @Override
    public List<ItemPedidoDto> buscarItemPedidoByidProducto(Long idProducto) {
        List<ItemPedido> pedidos = this.itemPedidoRepository.findByProductoId(idProducto);
        return this.itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedidos);
    }

    @Override
    public Double sumaTotalVentasDeidProducto(Long idProducto) {
        Double totalVentas = this.itemPedidoRepository.findTotalVentasByProducto(idProducto)
                .orElseThrow(() -> new RuntimeException("Error calculando total"));
        return totalVentas;

    }
}
