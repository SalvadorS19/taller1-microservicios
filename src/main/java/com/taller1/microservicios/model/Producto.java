package com.taller1.microservicios.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "productos", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
@NoArgsConstructor
@AllArgsConstructor
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
