package com.taller1.microservicios.service.detalleEnvio;

import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioDto;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioMapper;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioToSaveDto;
import com.taller1.microservicios.dto.DetalleEnvio.DetalleEnvioUpdateDto;
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
    public DetalleEnvioDto crearDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        Pedido pedido = this.pedidoRepository.findById(detalleEnvioToSaveDto.pedidoId())
                .orElseThrow(() -> new RuntimeException("No existe un pedido asociado al envio"));
        DetalleEnvio detalleEnvio = this.detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvio(detalleEnvioToSaveDto);
        LocalDateTime ahora = LocalDateTime.now();
        String numeroGuia = "ENV-" + detalleEnvio.getId() + ahora.getMonthValue() + ahora.getYear();
        detalleEnvio.setNumeroGuia(numeroGuia);
        detalleEnvio.setPedido(pedido);
        this.detalleEnvioRepository.save(detalleEnvio);
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public DetalleEnvioDto actualizarDetalleEnvio(Long id, DetalleEnvioUpdateDto detalleEnvioUpdateDto) {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El envio no existe"));
        detalleEnvio.setDireccion(detalleEnvioUpdateDto.direccion());
        detalleEnvio.setEstadoEnvio(detalleEnvioUpdateDto.estadoEnvio());
        detalleEnvio.setTransportadora(detalleEnvioUpdateDto.transportadora());
        this.detalleEnvioRepository.save(detalleEnvio);
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public DetalleEnvioDto buscarDetalleEnvioById(Long id) {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Los detalles del envio no existen"));
        return this.detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public void removerDetalleEnvio(Long id) {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Los detalles del envio no existen"));
        this.detalleEnvioRepository.delete(detalleEnvio);
    }

    @Override
    public List<DetalleEnvioDto> getAllDetalleEnvio() {
        return this.detalleEnvioMapper.detalleEnvioListToDetalleEnvioDtoList(this.detalleEnvioRepository.findAll());
    }

    @Override
    public DetalleEnvioDto getDetalleEnvioByPedidoId(Long id) {
        DetalleEnvio detalleEnvio = this.detalleEnvioRepository.findByPedidoId(id)
                .orElseThrow(() -> new RuntimeException("Los detalles del envio no existen"));
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
