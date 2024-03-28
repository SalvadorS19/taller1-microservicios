package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.DetalleEnvio;
import com.taller1.microservicios.model.enums.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, Long> {
    Optional<DetalleEnvio> findByPedidoId(Long pedido_id);

    List<DetalleEnvio> findByTransportadora(String transportadora);

    List<DetalleEnvio> findByEstadoEnvio(EstadoEnvio estadoEnvio);
}
