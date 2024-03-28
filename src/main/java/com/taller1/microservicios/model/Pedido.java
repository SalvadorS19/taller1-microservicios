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

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemsPedido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago pago;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalleEnvio_id", referencedColumnName = "id")
    private DetalleEnvio detalleEnvio;
}
