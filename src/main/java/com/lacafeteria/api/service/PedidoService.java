package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.PedidoRequest;
import com.lacafeteria.api.dto.response.PedidoResponse;
import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {

    List<PedidoResponse> listarTodos();

    List<PedidoResponse> listarPorEstado(EstadoPedido estado);

    List<PedidoResponse> listarPorModulo(Modulo modulo);

    List<PedidoResponse> listarPorEmpleado(Integer idEmpleado);

    List<PedidoResponse> listarPorCliente(Integer idCliente);

    List<PedidoResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta);

    PedidoResponse obtenerPorId(Integer id);

    PedidoResponse crear(PedidoRequest request);

    PedidoResponse cambiarEstado(Integer id, EstadoPedido estado);

    void cancelar(Integer id);
}
