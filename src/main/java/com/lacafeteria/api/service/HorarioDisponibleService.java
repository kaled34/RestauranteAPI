package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.HorarioDisponibleRequest;
import com.lacafeteria.api.dto.response.HorarioDisponibleResponse;

import java.util.List;

public interface HorarioDisponibleService {

    List<HorarioDisponibleResponse> listarTodos();

    List<HorarioDisponibleResponse> listarActivos();

    HorarioDisponibleResponse obtenerPorId(Integer id);

    HorarioDisponibleResponse crear(HorarioDisponibleRequest request);

    HorarioDisponibleResponse actualizar(Integer id, HorarioDisponibleRequest request);

    void eliminar(Integer id);
}
