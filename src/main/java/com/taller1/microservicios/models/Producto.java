package com.taller1.microservicios.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "productos", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
@Getter @Setter @Builder
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Double precio;

    private Integer stock;

    @OneToMany(mappedBy = "producto")
    private List<ItemPedido> itemPedidos;
}
