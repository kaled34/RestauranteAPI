package com.lacafeteria.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoLibreRequest {

    @NotNull(message = "El pedido es obligatorio")
    private Integer idPedido;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 200)
    private String descripcion;

    @NotNull(message = "El precio manual es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    private BigDecimal precioManual;

    @NotNull
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El admin es obligatorio")
    private Integer idAdmin;

    @Size(max = 200)
    private String notas;
}
