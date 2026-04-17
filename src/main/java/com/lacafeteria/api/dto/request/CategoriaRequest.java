package com.lacafeteria.api.dto.request;

import com.lacafeteria.api.entity.enums.Modulo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    private String nombre;

    @Size(max = 200, message = "La descripcion no puede superar 200 caracteres")
    private String descripcion;

    @NotNull(message = "El modulo es obligatorio")
    private Modulo modulo;

    private Boolean activo = true;
}
