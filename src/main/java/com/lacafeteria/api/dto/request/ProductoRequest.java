package com.lacafeteria.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRequest {

    @NotNull(message = "La categoria es obligatoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 300, message = "La descripcion no puede superar 300 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    private BigDecimal precio;

    private Boolean disponible = true;

    @Size(max = 255)
    private String imagenUrl;
}
