package com.lacafeteria.api.dto.request;

import com.lacafeteria.api.entity.enums.MetodoPago;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FacturaRequest {

    @NotNull(message = "El pedido es obligatorio")
    private Integer idPedido;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.00")
    private BigDecimal subtotal;

    @DecimalMin(value = "0.00")
    private BigDecimal descuento = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    private BigDecimal impuesto = BigDecimal.ZERO;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.00")
    private BigDecimal total;

    @NotNull(message = "El metodo de pago es obligatorio")
    private MetodoPago metodoPago;

    private String referenciaPago;
}
