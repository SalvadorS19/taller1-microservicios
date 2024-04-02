package com.taller1.microservicios.service.pago;

import com.taller1.microservicios.dto.pago.PagoDto;
import com.taller1.microservicios.dto.pago.PagoMapper;
import com.taller1.microservicios.dto.pago.PagoToSaveDto;
import com.taller1.microservicios.dto.pago.PagoUpdateDto;
import com.taller1.microservicios.exception.PagoNotFoundException;
import com.taller1.microservicios.exception.PedidoNotFoundException;
import com.taller1.microservicios.model.ItemPedido;
import com.taller1.microservicios.model.Pago;
import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.repository.PagoRepository;
import com.taller1.microservicios.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;
    private final PedidoRepository pedidoRepository;
    public PagoServiceImpl(
            PagoRepository pagoRepository,
            PagoMapper pagoMapper,
            PedidoRepository pedidoRepository
    ) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public PagoDto crearPago(PagoToSaveDto pagoToSaveDto) throws RuntimeException {
        Pedido pedido = this.pedidoRepository.findById(pagoToSaveDto.pedidoId())
                .orElseThrow(() -> new PedidoNotFoundException("No existe el pedido a pagar"));
        if (pedido.getPago() != null) {
            throw new RuntimeException("El pedido ya ha sido pagado");
        }
        List<ItemPedido> itemsPedido = pedido.getItemsPedido();
        if (itemsPedido.isEmpty()){
            throw new RuntimeException("El pedido no tiene productos para pagar");
        }
        Pago pago = this.pagoMapper.pagoToSaveDtoToPago(pagoToSaveDto);
        pago.setPedido(pedido);
        double totalPago = 0.0;
        for (ItemPedido itemPedido : itemsPedido) {
            totalPago += (itemPedido.getPrecioUnitario() * itemPedido.getCantidad());
        }
        pago.setTotalPago(totalPago);
        pago = this.pagoRepository.save(pago);
        pedido.setPago(pago);
        this.pedidoRepository.save(pedido);
        return this.pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public PagoDto actualizarPago(Long id, PagoUpdateDto pagoUpdateDto) throws PagoNotFoundException {
        Pago pago = this.pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("El pago no existe"));
        pago.setMetodoPago(pagoUpdateDto.metodoPago());
        pago = this.pagoRepository.save(pago);
        return this.pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public PagoDto buscarPagoById(Long id) throws PagoNotFoundException {
        Pago pago = this.pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("El pago no existe"));
        return this.pagoMapper.pagoToPagoDto(pago);
    }

    @Override
    public void removerPago(Long id) throws PagoNotFoundException {
        Pago pago = this.pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("El pago no existe"));
        this.pagoRepository.delete(pago);
    }

    @Override
    public List<PagoDto> getAllPagos() {
        return this.pagoMapper.pagoListToPagoDtoList(this.pagoRepository.findAll());
    }

    @Override
    public List<PagoDto> buscarPagosByRangoFecha(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Pago> pagos = this.pagoRepository.findByFechaPagoIsBetween(
                LocalDateTime.parse(fechaInicio, formatter),
                LocalDateTime.parse(fechaFin, formatter)
        );
        return this.pagoMapper.pagoListToPagoDtoList(pagos);
    }

    @Override
    public PagoDto buscarPagoByPedidoId(Long pedidoId) throws PagoNotFoundException {
        Pago pago = this.pagoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new PagoNotFoundException("El pago no existe"));
        return this.pagoMapper.pagoToPagoDto(pago);
    }
}
