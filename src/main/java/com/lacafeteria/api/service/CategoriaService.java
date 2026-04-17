package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.CategoriaRequest;
import com.lacafeteria.api.dto.response.CategoriaResponse;
import com.lacafeteria.api.entity.enums.Modulo;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponse> listarTodas();

    List<CategoriaResponse> listarPorModulo(Modulo modulo);

    List<CategoriaResponse> listarActivas();

    CategoriaResponse obtenerPorId(Integer id);

    CategoriaResponse crear(CategoriaRequest request);

    CategoriaResponse actualizar(Integer id, CategoriaRequest request);

    void eliminar(Integer id);
}
