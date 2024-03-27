package com.taller1.microservicios.repository;

import com.taller1.microservicios.models.Pago;
import com.taller1.microservicios.models.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Pago> findByMetodoPagoAndTotalPagoGreaterThanEqual(MetodoPago metodoPago, Double valorPago);
}
