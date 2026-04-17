package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.ReporteRequest;
import com.lacafeteria.api.dto.response.ReporteResponse;
import com.lacafeteria.api.entity.enums.TipoReporte;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {

    List<ReporteResponse> listarTodos();

    List<ReporteResponse> listarPorTipo(TipoReporte tipo);

    List<ReporteResponse> listarPorFecha(LocalDate desde, LocalDate hasta);

    ReporteResponse obtenerPorId(Integer id);

    ReporteResponse generar(ReporteRequest request);

    void eliminar(Integer id);
}
