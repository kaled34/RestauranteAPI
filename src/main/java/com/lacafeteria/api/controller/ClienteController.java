package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.ClienteRequest;
import com.lacafeteria.api.dto.request.LoginClienteRequest;
import com.lacafeteria.api.dto.response.ClienteResponse;
import com.lacafeteria.api.dto.response.LoginClienteResponse;
import com.lacafeteria.api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> listarTodos(
            @RequestParam(required = false) String telefono) {
        if (telefono != null) {
            return ResponseEntity.ok(ApiResponse.ok(service.buscarPorTelefono(telefono)));
        }
        return ResponseEntity.ok(ApiResponse.ok(service.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorEmail(email)));
    }

    /**
     * Login de cliente con email + contraseña (BCrypt).
     * La app Android llama a este endpoint en vez de solo buscar por email.
     * Devuelve ClienteResponse (los datos del cliente) si las credenciales son correctas.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<ClienteResponse>> login(
            @Valid @RequestBody LoginClienteRequest request) {
        ClienteResponse cliente = service.loginYObtener(request.getEmail(), request.getContrasena());
        return ResponseEntity.ok(ApiResponse.ok("Login exitoso", cliente));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> crear(
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cliente actualizado",
                service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Cliente eliminado", null));
    }
}