package com.lacafeteria.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class HorarioDisponibleResponse {

    private Integer idHorario;
    private LocalTime hora;
    private String etiqueta;
    private Boolean activo;
}
