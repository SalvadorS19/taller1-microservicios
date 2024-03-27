package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByDireccion(String direccion);

    List<Cliente> findByNombreStartsWith(String nombre); // TOCA CORREGIR
}
