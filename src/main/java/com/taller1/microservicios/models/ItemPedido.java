package com.taller1.microservicios.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items_pedidos")
@Getter @Setter @Builder
public class ItemPedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private Double precioUnitario;

    @ManyToOne @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne @JoinColumn(name = "producto_id")
    private Producto producto;
}