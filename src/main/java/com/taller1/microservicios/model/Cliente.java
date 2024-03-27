package com.taller1.microservicios.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
