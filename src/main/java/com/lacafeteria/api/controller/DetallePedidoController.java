package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.DetallePedidoRequest;
import com.lacafeteria.api.dto.response.DetallePedidoResponse;
import com.lacafeteria.api.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles")
@RequiredArgsConstructor
public class DetallePedidoController {

    private final DetallePedidoService service;

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<ApiResponse<List<DetallePedidoResponse>>> listarPorPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorPedido(idPedido)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetallePedidoResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @PostMapping("/pedido/{idPedido}")
    public ResponseEntity<ApiResponse<DetallePedidoResponse>> agregar(
            @PathVariable Integer idPedido, @Valid @RequestBody DetallePedidoRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.agregar(request, idPedido)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DetallePedidoResponse>> actualizar(
            @PathVariable Integer id, @Valid @RequestBody DetallePedidoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Detalle actualizado", service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Detalle eliminado", null));
    }
}
