package com.lacafeteria.api.dto.response;

import com.lacafeteria.api.entity.enums.RolEmpleado;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** La contrasena NUNCA se expone en la respuesta. */
@Data
@Builder
public class EmpleadoResponse {

    private Integer idEmpleado;
    private String nombre;
    private String apellido;
    private RolEmpleado rol;
    private String telefono;
    private String email;
    private Boolean activo;
    private LocalDateTime creadoEn;
}
