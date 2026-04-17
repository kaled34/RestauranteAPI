package com.lacafeteria.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class HorarioDisponibleRequest {

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    private String etiqueta;

    private Boolean activo = true;
}
