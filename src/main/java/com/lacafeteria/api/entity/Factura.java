package com.lacafeteria.api.entity;

import com.lacafeteria.api.entity.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_fac_ped"))
    private Pedido pedido;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal descuento;

    @Column(name = "impuesto", nullable = false, precision = 10, scale = 2)
    private BigDecimal impuesto;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "pagado", nullable = false)
    private Boolean pagado;

    @Column(name = "referencia_pago", length = 100)
    private String referenciaPago;

    @Column(name = "emitida_en", nullable = false, updatable = false)
    private LocalDateTime emitidaEn;

    @PrePersist
    void onPrePersist() {
        if (this.emitidaEn == null)
            this.emitidaEn = LocalDateTime.now();
        if (this.pagado == null)
            this.pagado = false;
        if (this.descuento == null)
            this.descuento = BigDecimal.ZERO;
        if (this.impuesto == null)
            this.impuesto = BigDecimal.ZERO;
        if (this.metodoPago == null)
            this.metodoPago = MetodoPago.efectivo;
    }
}
