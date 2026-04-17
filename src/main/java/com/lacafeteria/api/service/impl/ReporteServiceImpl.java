package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.ReporteRequest;
import com.lacafeteria.api.dto.response.ReporteResponse;
import com.lacafeteria.api.entity.Empleado;
import com.lacafeteria.api.entity.Reporte;
import com.lacafeteria.api.entity.enums.TipoReporte;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.EmpleadoRepository;
import com.lacafeteria.api.repository.ReporteRepository;
import com.lacafeteria.api.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository repo;
    private final EmpleadoRepository empleadoRepo;

    @Override
    public List<ReporteResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<ReporteResponse> listarPorTipo(TipoReporte tipo) {
        return repo.findByTipo(tipo).stream().map(this::toResponse).toList();
    }

    @Override
    public List<ReporteResponse> listarPorFecha(LocalDate desde, LocalDate hasta) {
        return repo.findByFechaInicioBetween(desde, hasta).stream().map(this::toResponse).toList();
    }

    @Override
    public ReporteResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public ReporteResponse generar(ReporteRequest req) {
        Empleado generadoPor = empleadoRepo.findById(req.getIdGeneradoPor())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", req.getIdGeneradoPor().longValue()));

        Reporte entity = Reporte.builder()
                .tipo(req.getTipo())
                .fechaInicio(req.getFechaInicio())
                .fechaFin(req.getFechaFin())
                .totalVentas(req.getTotalVentas())
                .totalPedidos(req.getTotalPedidos())
                .totalEfectivo(req.getTotalEfectivo())
                .totalTransferencia(req.getTotalTransferencia())
                .pedidosDesayunos(req.getPedidosDesayunos())
                .pedidosComidas(req.getPedidosComidas())
                .pedidosLibres(req.getPedidosLibres())
                .productoTop(req.getProductoTop())
                .generadoPor(generadoPor)
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Reporte findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte", id.longValue()));
    }

    private ReporteResponse toResponse(Reporte e) {
        return ReporteResponse.builder()
                .idReporte(e.getIdReporte())
                .tipo(e.getTipo())
                .fechaInicio(e.getFechaInicio())
                .fechaFin(e.getFechaFin())
                .totalVentas(e.getTotalVentas())
                .totalPedidos(e.getTotalPedidos())
                .totalEfectivo(e.getTotalEfectivo())
                .totalTransferencia(e.getTotalTransferencia())
                .pedidosDesayunos(e.getPedidosDesayunos())
                .pedidosComidas(e.getPedidosComidas())
                .pedidosLibres(e.getPedidosLibres())
                .productoTop(e.getProductoTop())
                .idGeneradoPor(e.getGeneradoPor().getIdEmpleado())
                .nombreGeneradoPor(e.getGeneradoPor().getNombre() + " " + e.getGeneradoPor().getApellido())
                .generadoEn(e.getGeneradoEn())
                .build();
    }
}
