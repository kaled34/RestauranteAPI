package com.lacafeteria.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginClienteResponse {

    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
}
