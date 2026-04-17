package com.lacafeteria.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PedidoLibreResponse {

    private Integer idLibre;
    private Integer idPedido;
    private String descripcion;
    private BigDecimal precioManual;
    private Integer cantidad;
    private BigDecimal subtotal;
    private Integer idAdmin;
    private String nombreAdmin;
    private String notas;
    private LocalDateTime creadoEn;
}
