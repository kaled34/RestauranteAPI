package com.lacafeteria.api.entity;

import com.lacafeteria.api.entity.enums.Modulo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "modulo", nullable = false)
    private Modulo modulo;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    void onPrePersist() {
        if (this.creadoEn == null)
            this.creadoEn = LocalDateTime.now();
        if (this.activo == null)
            this.activo = true;
        if (this.modulo == null)
            this.modulo = Modulo.comidas;
    }
}
