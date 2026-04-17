package com.lacafeteria.api.dto.response;

import com.lacafeteria.api.entity.enums.TipoReporte;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ReporteResponse {

    private Integer idReporte;
    private TipoReporte tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal totalVentas;
    private Integer totalPedidos;
    private BigDecimal totalEfectivo;
    private BigDecimal totalTransferencia;
    private Integer pedidosDesayunos;
    private Integer pedidosComidas;
    private Integer pedidosLibres;
    private String productoTop;
    private Integer idGeneradoPor;
    private String nombreGeneradoPor;
    private LocalDateTime generadoEn;
}
