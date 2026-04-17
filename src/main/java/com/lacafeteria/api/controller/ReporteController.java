package com.lacafeteria.api.controller;

import com.lacafeteria.api.common.ApiResponse;
import com.lacafeteria.api.dto.request.ReporteRequest;
import com.lacafeteria.api.dto.response.ReporteResponse;
import com.lacafeteria.api.entity.enums.TipoReporte;
import com.lacafeteria.api.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReporteResponse>>> listarTodos(
            @RequestParam(required = false) TipoReporte tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        List<ReporteResponse> lista;
        if (tipo != null) {
            lista = service.listarPorTipo(tipo);
        } else if (desde != null && hasta != null) {
            lista = service.listarPorFecha(desde, hasta);
        } else {
            lista = service.listarTodos();
        }
        return ResponseEntity.ok(ApiResponse.ok(lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReporteResponse>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPorId(id)));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<ReporteResponse>>> listarPorTipo(@PathVariable TipoReporte tipo) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPorTipo(tipo)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReporteResponse>> generar(@Valid @RequestBody ReporteRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.created(service.generar(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Reporte eliminado", null));
    }
}
