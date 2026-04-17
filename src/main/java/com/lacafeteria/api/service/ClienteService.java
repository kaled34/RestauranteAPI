package com.lacafeteria.api.service;

import com.lacafeteria.api.dto.request.ClienteRequest;
import com.lacafeteria.api.dto.response.ClienteResponse;
import com.lacafeteria.api.dto.response.LoginClienteResponse;

import java.util.List;

public interface ClienteService {

    List<ClienteResponse> listarTodos();

    List<ClienteResponse> buscarPorTelefono(String telefono);

    ClienteResponse obtenerPorId(Integer id);

    ClienteResponse obtenerPorEmail(String email);

    /** Verifica credenciales y devuelve los datos del cliente como ClienteResponse */
    ClienteResponse loginYObtener(String email, String contrasena);

    /** @deprecated Usar loginYObtener. Mantenido por compatibilidad */
    LoginClienteResponse login(String email, String contrasena);

    ClienteResponse crear(ClienteRequest request);

    ClienteResponse actualizar(Integer id, ClienteRequest request);

    void eliminar(Integer id);
}