package com.taller1.microservicios.service.detalleEnvio;

import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioMapper;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.dto.detalleEnvio.DetalleEnvioUpdateDto;
import com.taller1.microservicios.exception.DetalleEnvioNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.model.DetalleEnvio;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoEnvio;
import com.taller1.microservicios.repository.DetalleEnvioRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService {

    private final PedidoRepository pedidoRepository;
    private final DetalleEnvioRepository detalleEnvioRepository;
    private final DetalleEnvioMapper detalleEnvioMapper;

    public DetalleEnvioServiceImpl(
            DetalleEnvioRepository detalleEnvioRepository,
            DetalleEnvioMapper detalleEnvioMapper,
            PedidoRepository pedidoRepository)
    {
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.detalleEnvioMapper = detalleEnvioMapper;
        this.pedidoRepository = pedidoRepository;
    }
    @Override
    public DetalleEnvioDto crearDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto) throws PedidoNotFoundException {
        Pedido pedido = this.pedidoRepository.findById(detalleEnvioToSaveDto.pedidoId())
                .orElseThrow(() -> new PedidoNotFoundException("No existe un pedido asociado al envio"));
        DetalleEnvio detalleEnvio = this.detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvio(detalleEnvioToSaveDto);
        LocalDateTime ahora = LocalDateTime.now();
        String numeroGuia = "ENV-" + pedido.getId() + ahora.getMonthValue() + ahora.getYear();
        detalleEnvio.setNumeroGuia(numeroGuia);
        detalleEnvio.setPedido(pedido);
        detalleEnvio.setEstadoEnvio(EstadoEnvio.ENVIADO);
        detalleEnvio = this.detalleEnvioRepository.save(detalleEnvio);
        pedido.setDetalleEnvio(detalleEnvio);
        this.pedidoRepository.save(pedido);
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public DetalleEnvioDto actualizarDetalleEnvio(Long id, DetalleEnvioUpdateDto detalleEnvioUpdateDto) throws PedidoNotFoundException {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("El envio no existe"));
        detalleEnvio.setDireccion(detalleEnvioUpdateDto.direccion());
        detalleEnvio.setEstadoEnvio(detalleEnvioUpdateDto.estadoEnvio());
        detalleEnvio.setTransportadora(detalleEnvioUpdateDto.transportadora());
        detalleEnvio = this.detalleEnvioRepository.save(detalleEnvio);
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public DetalleEnvioDto buscarDetalleEnvioById(Long id) throws PedidoNotFoundException {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Los detalles del envio no existen"));
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public void removerDetalleEnvio(Long id) throws PedidoNotFoundException {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Los detalles del envio no existen"));
        this.detalleEnvioRepository.delete(detalleEnvio);
    }

    @Override
    public List<DetalleEnvioDto> getAllDetalleEnvio() {
        return this.detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(this.detalleEnvioRepository.findAll());
    }

    @Override
    public DetalleEnvioDto getDetalleEnvioByPedidoId(Long id) throws PedidoNotFoundException {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findByPedidoId(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Los detalles del envio no existen"));
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public List<DetalleEnvioDto> getDetalleEnviosByTransportadora(String transportadora) {
        List<DetalleEnvio> detalleEnvios = this.detalleEnvioRepository.findByTransportadora(transportadora);
        return this.detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(detalleEnvios);
    }

    @Override
    public List<DetalleEnvioDto> getDetalleEnviosByEstado(EstadoEnvio estadoEnvio) {
        List<DetalleEnvio> detalleEnvios = this.detalleEnvioRepository.findByEstadoEnvio(estadoEnvio);
        return this.detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(detalleEnvios);
    }
}
