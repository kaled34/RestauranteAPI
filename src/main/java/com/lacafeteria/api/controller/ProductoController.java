package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.ProductoRequest;
import com.lacafeteria.api.dto.response.ProductoResponse;
import com.lacafeteria.api.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarTodos(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Boolean disponible) {

        List<ProductoResponse> lista;
        if (categoriaId != null && disponible != null) {
            lista = service.listarPorCategoriaYDisponibilidad(categoriaId, disponible);
        } else if (categoriaId != null) {
            lista = service.listarPorCategoria(categoriaId);
        } else if (Boolean.TRUE.equals(disponible)) {
            lista = service.listarDisponibles();
        } else {
            lista = service.listarTodos();
        }
        return ResponseEntity.ok(ApiResponse.ok(lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarPorCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorCategoria(idCategoria)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> crear(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Producto actualizado", service.actualizar(id, request)));
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<ApiResponse<ProductoResponse>> cambiarDisponibilidad(
            @PathVariable Integer id, @RequestParam Boolean disponible) {
        return ResponseEntity.ok(ApiResponse.ok("Disponibilidad actualizada",
                service.cambiarDisponibilidad(id, disponible)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto eliminado", null));
    }
}
