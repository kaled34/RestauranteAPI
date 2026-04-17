package com.lacafeteria.api.dto.response;

import com.lacafeteria.api.entity.enums.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FacturaResponse {

    private Integer idFactura;
    private Integer idPedido;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal impuesto;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private Boolean pagado;
    private String referenciaPago;
    private LocalDateTime emitidaEn;
}
