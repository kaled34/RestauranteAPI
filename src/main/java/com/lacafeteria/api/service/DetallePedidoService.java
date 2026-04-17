package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.DetallePedidoRequest;
import com.lacafeteria.api.dto.response.DetallePedidoResponse;

import java.util.List;

public interface DetallePedidoService {

    List<DetallePedidoResponse> listarPorPedido(Integer idPedido);

    DetallePedidoResponse obtenerPorId(Integer id);

    DetallePedidoResponse agregar(DetallePedidoRequest request, Integer idPedido);

    DetallePedidoResponse actualizar(Integer id, DetallePedidoRequest request);

    void eliminar(Integer id);
}
