package com.taller1.microservicios.repository;

import com.taller1.microservicios.models.Pedido;
import com.taller1.microservicios.models.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByFechaPedidoBetween(LocalDateTime fecha1, LocalDateTime fecha2);

    List<Pedido> findByClienteAndEstado(Long clienteId, EstadoPedido estado);

    @Query("SELECT p FROM pedidos p JOIN FETCH p.itemsPedido itemsPedido WHERE p.cliente_id = ?1")
    List<Pedido> findAllByCliente(Long clienteId);
}
