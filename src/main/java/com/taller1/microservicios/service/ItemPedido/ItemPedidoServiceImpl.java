package com.taller1.microservicios.service.ItemPedido;

import com.taller1.microservicios.dto.ItemPedido.ItemPedidoDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoMapper;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoToSaveDto;
import com.taller1.microservicios.dto.ItemPedido.ItemPedidoUpdateDto;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{
    private final ItemPedidoMapper itemPedidoMapper;
    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoServiceImpl(ItemPedidoMapper itemPedidoMapper, ItemPedidoService itemPedidoService, ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public ItemPedidoDto crearItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto) {
        ItemPedido itemPedido= this.itemPedidoMapper.ItemPedidoToSaveDtoToItemPedido(itemPedidoToSaveDto);
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
        List<ItemPedido> pedidos = this.itemPedidoRepository.findByPedidoId(idProducto);
        return this.itemPedidoMapper.itemPedidoListToItemPedidoDtoList(pedidos);
    }

    @Override
    public Double sumaTotalVentasDeidProducto(Long idProducto) {
        Double totalVentas = this.itemPedidoRepository.findTotalVentasByProducto(idProducto)
                .orElseThrow(() -> new RuntimeException("Error calculando total"));
        return totalVentas;

    }
}
