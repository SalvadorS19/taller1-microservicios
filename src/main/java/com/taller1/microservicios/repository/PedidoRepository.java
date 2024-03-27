package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByFechaPedidoBetween(LocalDateTime fecha1, LocalDateTime fecha2);

    List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);

    @Query("SELECT p FROM Pedido p JOIN FETCH p.itemsPedido itemsPedido WHERE p.cliente.id = ?1")
    List<Pedido> findAllByCliente(Long clienteId);
}
