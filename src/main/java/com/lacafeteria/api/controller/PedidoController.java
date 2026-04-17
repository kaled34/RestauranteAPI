package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.PedidoRequest;
import com.lacafeteria.api.dto.response.PedidoResponse;
import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarTodos(
            @RequestParam(required = false) EstadoPedido estado,
            @RequestParam(required = false) Modulo modulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        List<PedidoResponse> lista;
        if (estado != null) {
            lista = service.listarPorEstado(estado);
        } else if (modulo != null) {
            lista = service.listarPorModulo(modulo);
        } else if (desde != null && hasta != null) {
            lista = service.listarPorFecha(desde, hasta);
        } else {
            lista = service.listarTodos();
        }
        return ResponseEntity.ok(ApiResponse.ok(lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarPorEstado(@PathVariable EstadoPedido estado) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorEstado(estado)));
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarPorEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorEmpleado(idEmpleado)));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorCliente(idCliente)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(@Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.crear(request)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<PedidoResponse>> cambiarEstado(
            @PathVariable Integer id, @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", service.cambiarEstado(id, estado)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelar(@PathVariable Integer id) {
        service.cancelar(id);
        return ResponseEntity.ok(ApiResponse.ok("Pedido cancelado", null));
    }
}
