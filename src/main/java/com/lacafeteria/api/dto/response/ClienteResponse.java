package com.lacafeteria.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClienteResponse {

    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDateTime creadoEn;
}
