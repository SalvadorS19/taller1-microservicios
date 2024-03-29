package com.taller1.microservicios.service.pago;

import com.taller1.microservicios.dto.Pago.PagoDto;
import com.taller1.microservicios.dto.Pago.PagoMapper;
import com.taller1.microservicios.dto.Pago.PagoToSaveDto;
import com.taller1.microservicios.dto.Pago.PagoUpdateDto;
import com.taller1.microservicios.model.Pago;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService{

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    public PagoServiceImpl(PagoRepository pagoRepository, PagoMapper pagoMapper) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
    }

    @Override
    public PagoDto crearPago(PagoToSaveDto pagoToSaveDto) {
        this.pagoRepository.findById(pagoToSaveDto.pedidoId())
                .orElseThrow(() -> new RuntimeException("No existe el pedido a pagar"));
        LocalDateTime ahora= LocalDateTime.now();
        Pago pago= this.pagoMapper.pagoToSaveDtoToPago(pagoToSaveDto);
        pago.setFechaPago(ahora);
        this.pagoRepository.save(pago);
        return this.pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public PagoDto actualizarPago(Long id, PagoUpdateDto pagoUpdateDto) {
        Pago pago = this.pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("pago no existe"));
        pago.setMetodoPago(pagoUpdateDto.metodoPago());
        this.pagoRepository.save(pago);
        return this.pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public PagoDto buscarPagoById(Long id) {
        Pago pago = this.pagoRepository.findById(id).orElseThrow(() -> new RuntimeException("El pago no existe"));
        return this.pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public void removerPago(Long id) {
        Pago pago = this.pagoRepository.findById(id).orElseThrow(() -> new RuntimeException("El pago no existe"));
            this.pagoRepository.delete(pago);
    }

    @Override
    public List<PagoDto> getAllPagos() {
        return this.pagoMapper.pagoListToPagoDtoList(this.pagoRepository.findAll());
    }

    @Override
    public List<PagoDto> buscarPagosByRangoFecha(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Pago> pagos = this.pagoRepository.findByFechaPagoBetween(
                LocalDateTime.parse(fechaInicio, formatter),
                LocalDateTime.parse(fechaInicio, formatter)
        );
        return this.pagoMapper.pagoListToPagoDtoList(pagos);
    }

    @Override
    public PagoDto buscarPagoByPedidoId(Long pedidoId) {
        Pago pago = this.pagoRepository.findByPedidoId(pedidoId).orElseThrow(() -> new RuntimeException("El pago no existe"));
        return this.pagoMapper.pagoToPagoDto(pago);
    }
}
