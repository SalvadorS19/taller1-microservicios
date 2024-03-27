package com.taller1.microservicios.repository;

import com.taller1.microservicios.models.DetalleEnvio;
import com.taller1.microservicios.models.enums.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, Long> {
    DetalleEnvio findByPedido(Long pedido_id);

    List<DetalleEnvio> findByTransportadora(String transportadora);

    List<DetalleEnvio> findByEstadoEnvio(EstadoEnvio estadoEnvio);
}
