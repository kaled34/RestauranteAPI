package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.HorarioDisponibleRequest;
import com.lacafeteria.api.dto.response.HorarioDisponibleResponse;
import com.lacafeteria.api.service.HorarioDisponibleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/horarios")
@RequiredArgsConstructor
public class HorarioDisponibleController {

    private final HorarioDisponibleService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HorarioDisponibleResponse>>> listarTodos(
            @RequestParam(required = false) Boolean activo) {
        if (Boolean.TRUE.equals(activo)) {
            return ResponseEntity.ok(ApiResponse.ok(service.listarActivos()));
        }
        return ResponseEntity.ok(ApiResponse.ok(service.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HorarioDisponibleResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HorarioDisponibleResponse>> crear(
            @Valid @RequestBody HorarioDisponibleRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HorarioDisponibleResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody HorarioDisponibleRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Horario actualizado", service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Horario eliminado", null));
    }
}
