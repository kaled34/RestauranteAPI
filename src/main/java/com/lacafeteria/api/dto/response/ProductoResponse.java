package com.lacafeteria.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductoResponse {

    private Integer idProducto;
    private Integer idCategoria;
    private String nombreCategoria;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Boolean disponible;
    private String imagenUrl;
    private LocalDateTime creadoEn;
}
