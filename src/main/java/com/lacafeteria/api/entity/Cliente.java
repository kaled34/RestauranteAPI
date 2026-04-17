package com.lacafeteria.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad Cliente.
 * El campo contrasena almacena el hash BCrypt de la contraseña del cliente.
 * La app Android envía la contraseña en texto plano al endpoint /api/v1/clientes
 * y /api/v1/clientes/login; el servidor aplica BCrypt antes de guardar/comparar.
 */
@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellido", length = 60)
    private String apellido;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    /** Hash BCrypt de la contraseña. Nunca se expone en las respuestas. */
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    void onPrePersist() {
        if (this.creadoEn == null)
            this.creadoEn = LocalDateTime.now();
    }
}