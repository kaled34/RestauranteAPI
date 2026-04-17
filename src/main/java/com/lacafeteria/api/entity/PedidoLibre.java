package com.lacafeteria.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos_libres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoLibre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libre")
    private Integer idLibre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, foreignKey = @ForeignKey(name = "fk_libre_ped"))
    private Pedido pedido;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "precio_manual", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioManual;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    /**
     * Columna generada en MySQL (STORED): cantidad * precio_manual.
     */
    @Column(name = "subtotal", insertable = false, updatable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin", nullable = false, foreignKey = @ForeignKey(name = "fk_libre_admin"))
    private Empleado admin;

    @Column(name = "notas", length = 200)
    private String notas;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    void onPrePersist() {
        if (this.creadoEn == null)
            this.creadoEn = LocalDateTime.now();
        if (this.cantidad == null)
            this.cantidad = 1;
    }
}
