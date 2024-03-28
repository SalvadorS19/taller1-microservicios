package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedidoId(Long pedidoId);

    List<ItemPedido> findByProductoId(Long productoId);

    @Query("SELECT SUM(p.cantidad * p.precioUnitario) FROM ItemPedido p where p.producto.id = :productoId")
    Optional<Double> findTotalVentasByProducto(Long productoId);
}
