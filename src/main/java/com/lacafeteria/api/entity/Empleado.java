package com.lacafeteria.api.entity;

import com.lacafeteria.api.entity.enums.RolEmpleado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "empleados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 60)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolEmpleado rol;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    /** Siempre almacenar hash BCrypt, nunca texto plano. */
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

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
    }
}
