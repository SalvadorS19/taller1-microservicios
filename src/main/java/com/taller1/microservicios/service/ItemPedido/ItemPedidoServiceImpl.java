package com.taller1.microservicios.service.ItemPedido;

import com.taller1.microservicios.dto.itemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoMapper;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.itemPedido.ItemPedidoUpdateDto;
import com.taller1.microservicios.exception.ItemPedidoNotFoundException;
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
    public ItemPedidoDto crearItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto) throws RuntimeException {
        boolean itemPedidoExists = this.itemPedidoRepository.findByProductoIdAndPedidoId(
                itemPedidoToSaveDto.productoId(),
                itemPedidoToSaveDto.pedidoId()
        ).isPresent();
        if (itemPedidoExists) throw new RuntimeException("El item pedido ya existe");
        Pedido pedido = this.pedidoRepository.findById(itemPedidoToSaveDto.pedidoId())
                .orElseThrow(() -> new PedidoNotFoundException("El pedido no existe"));
        Producto producto = this.productoRepository.findById(itemPedidoToSaveDto.productoId())
                .orElseThrow(() -> new ProductoNotFoundException("El producto no existe"));
        ItemPedido itemPedido = this.itemPedidoMapper.ItemPedidoToSaveDtoToItemPedido(itemPedidoToSaveDto);
        itemPedido.setPedido(pedido);
        itemPedido.setProducto(producto);
        itemPedido.setPrecioUnitario(producto.getPrecio());
        itemPedido = itemPedidoRepository.save(itemPedido);
        return this.itemPedidoMapper.ItemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public List<ItemPedidoDto> getAllItemPedidos() {
        List<ItemPedido> pedidos = this.itemPedidoRepository.findAll();
        return this.itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedidos);
    }

    @Override
    public ItemPedidoDto buscarItemPedidoById(Long id) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = this.itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no existe"));
        return this.itemPedidoMapper.ItemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoUpdateDto itemPedidoUpdateDto) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = this.itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no existe"));
        itemPedido.setCantidad(itemPedidoUpdateDto.cantidad());
        itemPedido = itemPedidoRepository.save(itemPedido);
        return this.itemPedidoMapper.ItemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public void removerItemPedido(Long id) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = this.itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no existe"));
        itemPedidoRepository.delete(itemPedido);
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
    public Double sumaTotalVentasDeidProducto(Long idProducto) throws RuntimeException {
        return this.itemPedidoRepository.findTotalVentasByProducto(idProducto)
                .orElseThrow(() -> new RuntimeException("Error calculando total"));
    }
}
