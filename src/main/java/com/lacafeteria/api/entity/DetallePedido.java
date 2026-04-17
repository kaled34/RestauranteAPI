package com.lacafeteria.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, foreignKey = @ForeignKey(name = "fk_det_ped"))
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name = "fk_det_prod"))
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    /**
     * Columna generada en MySQL (STORED): cantidad * precio_unitario.
     * insertable=false y updatable=false para que Hibernate no la gestione.
     */
    @Column(name = "subtotal", insertable = false, updatable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "notas", length = 200)
    private String notas;

    @PrePersist
    void onPrePersist() {
        if (this.cantidad == null)
            this.cantidad = 1;
    }
}
