package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Pedido;
import com.taller1.microservicios.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByFechaPedidoBetween(LocalDateTime fecha1, LocalDateTime fecha2);

    List<Pedido> findByClienteIdAndEstadoPedido(Long clienteId, EstadoPedido estadoPedido);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.itemsPedido WHERE p.cliente.id = :clienteId")
    List<Pedido> findPedidoConProductosByCliente(Long clienteId);
}
