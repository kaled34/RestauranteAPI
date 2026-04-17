package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.FacturaRequest;
import com.lacafeteria.api.dto.response.FacturaResponse;
import com.lacafeteria.api.entity.enums.MetodoPago;
import com.lacafeteria.api.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FacturaResponse>>> listarTodas(
            @RequestParam(required = false) Boolean pagado,
            @RequestParam(required = false) MetodoPago metodoPago,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        List<FacturaResponse> lista;
        if (pagado != null) {
            lista = service.listarPorPagado(pagado);
        } else if (metodoPago != null) {
            lista = service.listarPorMetodoPago(metodoPago);
        } else if (desde != null && hasta != null) {
            lista = service.listarPorFecha(desde, hasta);
        } else {
            lista = service.listarTodas();
        }
        return ResponseEntity.ok(ApiResponse.ok(lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FacturaResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<ApiResponse<FacturaResponse>> obtenerPorPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorPedido(idPedido)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FacturaResponse>> emitir(@Valid @RequestBody FacturaRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.emitir(request)));
    }

    @PatchMapping("/{id}/pago")
    public ResponseEntity<ApiResponse<FacturaResponse>> marcarComoPagada(
            @PathVariable Integer id,
            @RequestParam(required = false) String referenciaPago) {
        return ResponseEntity.ok(ApiResponse.ok("Factura marcada como pagada",
                service.marcarComoPagada(id, referenciaPago)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Factura eliminada", null));
    }
}
