package com.lacafeteria.api.dto.request;

import com.lacafeteria.api.entity.enums.TipoReporte;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReporteRequest {

    @NotNull(message = "El tipo de reporte es obligatorio")
    private TipoReporte tipo;

    @NotNull(message = "La fecha inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha fin es obligatoria")
    private LocalDate fechaFin;

    private BigDecimal totalVentas = BigDecimal.ZERO;
    private Integer totalPedidos = 0;
    private BigDecimal totalEfectivo = BigDecimal.ZERO;
    private BigDecimal totalTransferencia = BigDecimal.ZERO;
    private Integer pedidosDesayunos = 0;
    private Integer pedidosComidas = 0;
    private Integer pedidosLibres = 0;
    private String productoTop;

    @NotNull(message = "El empleado generador es obligatorio")
    private Integer idGeneradoPor;
}
