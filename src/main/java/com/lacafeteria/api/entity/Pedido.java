package com.lacafeteria.api.entity;

import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.entity.enums.TipoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false, foreignKey = @ForeignKey(name = "fk_ped_emp"))
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", foreignKey = @ForeignKey(name = "fk_ped_cli"))
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "modulo", nullable = false)
    private Modulo modulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPedido estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPedido tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario_recogida", foreignKey = @ForeignKey(name = "fk_ped_horario"))
    private HorarioDisponible horarioRecogida;

    @Column(name = "notas", length = 300)
    private String notas;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @PrePersist
    void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.creadoEn == null)
            this.creadoEn = now;
        if (this.actualizadoEn == null)
            this.actualizadoEn = now;
        if (this.estado == null)
            this.estado = EstadoPedido.pendiente;
        if (this.modulo == null)
            this.modulo = Modulo.comidas;
        if (this.tipo == null)
            this.tipo = TipoPedido.para_llevar;
    }

    @PreUpdate
    void onPreUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }
}
