package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Pago;
import com.taller1.microservicios.model.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Optional<Pago> findByPedidoId(Long pedidoId);
}
