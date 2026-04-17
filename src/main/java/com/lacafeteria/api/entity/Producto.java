package com.lacafeteria.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false, foreignKey = @ForeignKey(name = "fk_prod_cat"))
    private Categoria categoria;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    void onPrePersist() {
        if (this.creadoEn == null)
            this.creadoEn = LocalDateTime.now();
        if (this.disponible == null)
            this.disponible = true;
    }
}
