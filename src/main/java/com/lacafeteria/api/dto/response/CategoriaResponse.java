package com.lacafeteria.api.dto.response;

import com.lacafeteria.api.entity.enums.Modulo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoriaResponse {

    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private Modulo modulo;
    private Boolean activo;
    private LocalDateTime creadoEn;
}
