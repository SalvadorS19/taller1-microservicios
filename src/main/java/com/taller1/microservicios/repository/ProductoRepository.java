package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("select p from Producto p where p.stock >= 1")
    List<Producto> findByInStock();
    List<Producto> findByPrecioLessThanEqualAndStockLessThanEqual(Double precio, Integer stock);

}
