package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.PedidoLibreRequest;
import com.lacafeteria.api.dto.response.PedidoLibreResponse;
import com.lacafeteria.api.service.PedidoLibreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos-libres")
@RequiredArgsConstructor
public class PedidoLibreController {

    private final PedidoLibreService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoLibreResponse>>> listarTodos() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoLibreResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<ApiResponse<List<PedidoLibreResponse>>> listarPorPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorPedido(idPedido)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoLibreResponse>> crear(@Valid @RequestBody PedidoLibreRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoLibreResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody PedidoLibreRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Pedido libre actualizado", service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Pedido libre eliminado", null));
    }
}
