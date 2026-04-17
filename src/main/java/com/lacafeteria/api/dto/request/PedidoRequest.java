package com.lacafeteria.api.dto.request;

import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.entity.enums.TipoPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {

    @NotNull(message = "El empleado es obligatorio")
    private Integer idEmpleado;

    private Integer idCliente;

    @NotNull(message = "El modulo es obligatorio")
    private Modulo modulo;

    @NotNull(message = "El tipo es obligatorio")
    private TipoPedido tipo;

    private Integer idHorarioRecogida;

    private String notas;

    private EstadoPedido estado = EstadoPedido.pendiente;

    /** Items que conforman el pedido (solo para modulo desayunos/comidas). */
    @Valid
    private List<DetallePedidoRequest> detalles;
}
