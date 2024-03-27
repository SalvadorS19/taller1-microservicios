package com.taller1.microservicios.repository;

import com.taller1.microservicios.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);

    Cliente findByDireccion(String direccion);

    List<Cliente> findByNombreStartingWith(String name);
}
