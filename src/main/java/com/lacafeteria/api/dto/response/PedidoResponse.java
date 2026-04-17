package com.lacafeteria.api.dto.response;

import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.entity.enums.TipoPedido;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponse {

    private Integer idPedido;
    private Integer idEmpleado;
    private String nombreEmpleado;
    private Integer idCliente;
    private String nombreCliente;
    private Modulo modulo;
    private EstadoPedido estado;
    private TipoPedido tipo;
    private Integer idHorarioRecogida;
    private String etiquetaHorario;
    private String notas;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private List<DetallePedidoResponse> detalles;
}
