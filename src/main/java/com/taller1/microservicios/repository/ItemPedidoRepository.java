package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedidoId(Long pedido_id);

    List<ItemPedido> findByProductoId(Long producto_id);

    @Query("SELECT SUM(p.cantidad * p.precioUnitario) FROM ItemPedido p where p.id = ?1")
    Optional<Double> findTotalVentasByProducto(Long producto_id);
}
