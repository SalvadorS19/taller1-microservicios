package com.taller1.microservicios.service.pedido;

import com.taller1.microservicios.dto.pedido.*;
import com.taller1.microservicios.exception.ClienteNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.model.Cliente;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import com.taller1.microservicios.repository.ClienteRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            PedidoMapper pedidoMapper,
            ClienteRepository clienteRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.clienteRepository = clienteRepository;
    }
    @Override
    public PedidoDto crearPedido(PedidoToSaveDto pedidoToSaveDto) throws ClienteNotFoundException {
        Cliente cliente = this.clienteRepository.findById(pedidoToSaveDto.clienteId())
                .orElseThrow(()-> new ClienteNotFoundException("No existe el cliente"));
        Pedido pedido = this.pedidoMapper.pedidoToSaveDtoToPedido(pedidoToSaveDto);
        pedido.setCliente(cliente);
        pedido = this.pedidoRepository.save(pedido);
        return this.pedidoMapper.pedidoToPedidoDto(pedido);
    }

    @Override
    public PedidoDto actualizarPedido(Long id, PedidoToUpdateDto pedidoToUpdateDto) throws PedidoNotFoundException {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(()-> new PedidoNotFoundException("No existe el pedido"));
        pedido.setEstadoPedido(pedidoToUpdateDto.estadoPedido());
        pedido = this.pedidoRepository.save(pedido);
        return this.pedidoMapper.pedidoToPedidoDto(pedido);
    }

    @Override
    public PedidoDto buscarPedidoById(Long id) throws PedidoNotFoundException {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(()-> new PedidoNotFoundException("No existe el pedido"));
        return this.pedidoMapper.pedidoToPedidoDto(pedido);
    }

    @Override
    public void removerPedido(Long id) throws PedidoNotFoundException {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(()-> new PedidoNotFoundException("No existe el pedido"));
        this.pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDto> getAllPedidos() {
        return this.pedidoMapper.pedidoListToPedidoDtoList(this.pedidoRepository.findAll());
    }

    @Override
    public List<PedidoDto> buscarPedidosByRangoFechas(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Pedido> pedidos = this.pedidoRepository.findByFechaPedidoBetween(
                LocalDateTime.parse(fechaInicio, formatter),
                LocalDateTime.parse(fechaFin, formatter)
        );
        return this.pedidoMapper.pedidoListToPedidoDtoList(pedidos);
    }

    @Override
    public List<PedidoDto> buscarPedidoByClienteIdAndEstado(Long clienteId, EstadoPedido estadoPedido) {
        List<Pedido> pedidos = this.pedidoRepository.findByClienteIdAndEstadoPedido(clienteId, estadoPedido);
        return this.pedidoMapper.pedidoListToPedidoDtoList(pedidos);
    }

    @Override
    public List<PedidoProductosDto> buscarPedidosConProductos(Long clienteId) {
        List<Pedido> pedidos = this.pedidoRepository.findPedidoConProductosByCliente(clienteId);
        return this.pedidoMapper.pedidoListToPedidoProductosListDto(pedidos);
    }
}
