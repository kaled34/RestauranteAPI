package com.lacafeteria.api.dto.response;

import com.lacafeteria.api.entity.enums.RolEmpleado;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginEmpleadoResponse {

    private Integer idEmpleado;
    private String nombre;
    private String apellido;
    private RolEmpleado rol;
    private String email;
}