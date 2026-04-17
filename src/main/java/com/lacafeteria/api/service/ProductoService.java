package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.ProductoRequest;
import com.lacafeteria.api.dto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listarTodos();

    List<ProductoResponse> listarPorCategoria(Integer idCategoria);

    List<ProductoResponse> listarDisponibles();

    List<ProductoResponse> listarPorCategoriaYDisponibilidad(Integer idCategoria, Boolean disponible);

    ProductoResponse obtenerPorId(Integer id);

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse actualizar(Integer id, ProductoRequest request);

    ProductoResponse cambiarDisponibilidad(Integer id, Boolean disponible);

    void eliminar(Integer id);
}
