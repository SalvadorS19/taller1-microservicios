package com.taller1.microservicios.repository;

import com.taller1.microservicios.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);

    @Query("select p from productos p where p.stock >= 1")
    List<Producto> findByInStock();

    List<Producto> findByPrecioLessThanEqualAndStockLessThanEqual(Double precio, Integer stock);

}
