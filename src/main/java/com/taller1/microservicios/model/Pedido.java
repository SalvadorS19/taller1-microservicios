package com.taller1.microservicios.model;

import com.taller1.microservicios.model.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Pedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private List<ItemPedido> itemsPedido;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private Pago pago;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private DetalleEnvio detalleEnvio;
}
