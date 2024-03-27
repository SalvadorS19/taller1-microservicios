package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.DetalleEnvio;
import com.taller1.microservicios.model.enums.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, Long> {
    DetalleEnvio findByPedidoId(Long pedido_id);

    List<DetalleEnvio> findByTransportadora(String transportadora);

    List<DetalleEnvio> findByEstadoEnvio(EstadoEnvio estadoEnvio);
}
