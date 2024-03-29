package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Pago;
import com.taller1.microservicios.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByFechaPagoBetween(LocalDateTime fecha1, LocalDateTime fecha2);

    Optional<Pago> findByPedidoId(Long pedidoId);
}
