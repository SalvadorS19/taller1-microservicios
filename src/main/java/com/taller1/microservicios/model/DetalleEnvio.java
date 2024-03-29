package com.taller1.microservicios.model;

import com.taller1.microservicios.model.enums.EstadoEnvio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_envios", uniqueConstraints = {@UniqueConstraint(columnNames = "numeroGuia")})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class DetalleEnvio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;

    private String transportadora;

    private EstadoEnvio estadoEnvio;

    @Column(unique = true)
    private String numeroGuia;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
