package com.taller1.microservicios.models;

import com.taller1.microservicios.models.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter @Setter @Builder
public class Pago {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPago;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @OneToOne(mappedBy = "pago")
    private Pedido pedido;
}
