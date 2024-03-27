package com.taller1.microservicios.repository;

import com.taller1.microservicios.models.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Long pedido_id);

    List<ItemPedido> findByProducto(Long producto_id);

    @Query("SELECT SUM(p.cantidad * p.precioUnitario) FROM productos p")
    Double findTotalVentasByProducto(Long producto_id);
}
