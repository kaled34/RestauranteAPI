package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.CategoriaRequest;
import com.lacafeteria.api.dto.response.CategoriaResponse;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listarTodas() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarTodas()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/modulo/{modulo}")
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listarPorModulo(@PathVariable Modulo modulo) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorModulo(modulo)));
    }

    @GetMapping("/activas")
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listarActivas() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarActivas()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponse>> crear(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Categoria actualizada", service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Categoria eliminada", null));
    }
}
