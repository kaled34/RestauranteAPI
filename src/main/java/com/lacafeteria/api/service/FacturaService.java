package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.FacturaRequest;
import com.lacafeteria.api.dto.response.FacturaResponse;
import com.lacafeteria.api.entity.enums.MetodoPago;

import java.time.LocalDateTime;
import java.util.List;

public interface FacturaService {

    List<FacturaResponse> listarTodas();

    List<FacturaResponse> listarPorPagado(Boolean pagado);

    List<FacturaResponse> listarPorMetodoPago(MetodoPago metodoPago);

    List<FacturaResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta);

    FacturaResponse obtenerPorId(Integer id);

    FacturaResponse obtenerPorPedido(Integer idPedido);

    FacturaResponse emitir(FacturaRequest request);

    FacturaResponse marcarComoPagada(Integer id, String referenciaPago);

    void eliminar(Integer id);
}
