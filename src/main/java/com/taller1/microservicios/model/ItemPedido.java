package com.taller1.microservicios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items_pedidos")
@NoArgsConstructor
@AllArgsConstructor
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
