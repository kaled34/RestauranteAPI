package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.PedidoLibreRequest;
import com.lacafeteria.api.dto.response.PedidoLibreResponse;

import java.util.List;

public interface PedidoLibreService {

    List<PedidoLibreResponse> listarTodos();

    List<PedidoLibreResponse> listarPorPedido(Integer idPedido);

    PedidoLibreResponse obtenerPorId(Integer id);

    PedidoLibreResponse crear(PedidoLibreRequest request);

    PedidoLibreResponse actualizar(Integer id, PedidoLibreRequest request);

    void eliminar(Integer id);
}
