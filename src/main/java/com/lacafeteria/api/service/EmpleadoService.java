package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.EmpleadoRequest;
import com.lacafeteria.api.dto.response.EmpleadoResponse;
import com.lacafeteria.api.dto.response.LoginEmpleadoResponse;
import com.lacafeteria.api.entity.enums.RolEmpleado;

import java.util.List;

public interface EmpleadoService {

    List<EmpleadoResponse> listarTodos();

    List<EmpleadoResponse> listarPorRol(RolEmpleado rol);

    List<EmpleadoResponse> listarActivos();

    EmpleadoResponse obtenerPorId(Integer id);

    EmpleadoResponse crear(EmpleadoRequest request);

    EmpleadoResponse actualizar(Integer id, EmpleadoRequest request);

    EmpleadoResponse cambiarActivo(Integer id, Boolean activo);

    LoginEmpleadoResponse login(String email, String contrasena);

    void eliminar(Integer id);
}
