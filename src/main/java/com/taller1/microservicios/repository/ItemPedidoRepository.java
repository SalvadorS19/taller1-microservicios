package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findByPedidoId(Long pedidoId);

    List<ItemPedido> findByProductoId(Long productoId);

    @Query("SELECT SUM(ip.cantidad * ip.precioUnitario) FROM ItemPedido ip WHERE ip.producto.id = :productoId")
    Optional<Double> findTotalVentasByProducto(Long productoId);

    // Validaciones
    Optional<ItemPedido> findByProductoIdAndPedidoId(Long productoId, Long pedidoId);
}
