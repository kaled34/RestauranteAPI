package com.lacafeteria.api.entity;

import com.lacafeteria.api.entity.enums.TipoReporte;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoReporte tipo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "total_ventas", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalVentas;

    @Column(name = "total_pedidos", nullable = false)
    private Integer totalPedidos;

    @Column(name = "total_efectivo", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalEfectivo;

    @Column(name = "total_transferencia", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalTransferencia;

    @Column(name = "pedidos_desayunos", nullable = false)
    private Integer pedidosDesayunos;

    @Column(name = "pedidos_comidas", nullable = false)
    private Integer pedidosComidas;

    @Column(name = "pedidos_libres", nullable = false)
    private Integer pedidosLibres;

    @Column(name = "producto_top", length = 100)
    private String productoTop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generado_por", nullable = false, foreignKey = @ForeignKey(name = "fk_rep_emp"))
    private Empleado generadoPor;

    @Column(name = "generado_en", nullable = false, updatable = false)
    private LocalDateTime generadoEn;

    @PrePersist
    void onPrePersist() {
        if (this.generadoEn == null)
            this.generadoEn = LocalDateTime.now();
        if (this.totalVentas == null)
            this.totalVentas = BigDecimal.ZERO;
        if (this.totalEfectivo == null)
            this.totalEfectivo = BigDecimal.ZERO;
        if (this.totalTransferencia == null)
            this.totalTransferencia = BigDecimal.ZERO;
        if (this.totalPedidos == null)
            this.totalPedidos = 0;
        if (this.pedidosDesayunos == null)
            this.pedidosDesayunos = 0;
        if (this.pedidosComidas == null)
            this.pedidosComidas = 0;
        if (this.pedidosLibres == null)
            this.pedidosLibres = 0;
    }
}
