package com.lacafeteria.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "horarios_disponibles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;

    @Column(name = "hora", nullable = false, unique = true)
    private LocalTime hora;

    @Column(name = "etiqueta", length = 60)
    private String etiqueta;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @PrePersist
    void onPrePersist() {
        if (this.activo == null)
            this.activo = true;
    }
}
