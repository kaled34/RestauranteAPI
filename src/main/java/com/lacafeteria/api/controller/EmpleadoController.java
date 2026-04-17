package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.EmpleadoRequest;
import com.lacafeteria.api.dto.request.LoginEmpleadoRequest;
import com.lacafeteria.api.dto.response.EmpleadoResponse;
import com.lacafeteria.api.dto.response.LoginEmpleadoResponse;
import com.lacafeteria.api.entity.enums.RolEmpleado;
import com.lacafeteria.api.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpleadoResponse>>> listarTodos() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<ApiResponse<List<EmpleadoResponse>>> listarPorRol(@PathVariable RolEmpleado rol) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorRol(rol)));
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<EmpleadoResponse>>> listarActivos() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarActivos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpleadoResponse>> crear(@Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.ok("Empleado creado correctamente", service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Empleado actualizado", service.actualizar(id, request)));
    }

    @PatchMapping("/{id}/activo")
    public ResponseEntity<ApiResponse<EmpleadoResponse>> cambiarActivo(
            @PathVariable Integer id, @RequestParam Boolean activo) {
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", service.cambiarActivo(id, activo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Empleado eliminado", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginEmpleadoResponse>> login(
            @Valid @RequestBody LoginEmpleadoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Login exitoso",
                service.login(request.getEmail(), request.getContrasena())));
    }

}
