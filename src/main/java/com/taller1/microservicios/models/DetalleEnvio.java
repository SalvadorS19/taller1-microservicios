package com.taller1.microservicios.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detalle_envios", uniqueConstraints = {@UniqueConstraint(columnNames = "numeroGuia")})
@Getter @Setter @Builder
public class DetalleEnvio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;

    private String transportadora;

    @Column(unique = true)
    private String numeroGuia;

    @OneToOne(mappedBy = "detalleEnvio")
    private Pedido pedido;
}
