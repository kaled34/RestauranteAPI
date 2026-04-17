package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.HorarioDisponibleRequest;
import com.lacafeteria.api.dto.response.HorarioDisponibleResponse;
import com.lacafeteria.api.entity.HorarioDisponible;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.HorarioDisponibleRepository;
import com.lacafeteria.api.service.HorarioDisponibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    private final HorarioDisponibleRepository repo;

    @Override
    public List<HorarioDisponibleResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<HorarioDisponibleResponse> listarActivos() {
        return repo.findByActivo(true).stream().map(this::toResponse).toList();
    }

    @Override
    public HorarioDisponibleResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public HorarioDisponibleResponse crear(HorarioDisponibleRequest req) {
        HorarioDisponible entity = HorarioDisponible.builder()
                .hora(req.getHora())
                .etiqueta(req.getEtiqueta())
                .activo(req.getActivo() != null ? req.getActivo() : true)
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public HorarioDisponibleResponse actualizar(Integer id, HorarioDisponibleRequest req) {
        HorarioDisponible entity = findOrThrow(id);
        entity.setHora(req.getHora());
        entity.setEtiqueta(req.getEtiqueta());
        if (req.getActivo() != null)
            entity.setActivo(req.getActivo());
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private HorarioDisponible findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HorarioDisponible", id.longValue()));
    }

    private HorarioDisponibleResponse toResponse(HorarioDisponible e) {
        return HorarioDisponibleResponse.builder()
                .idHorario(e.getIdHorario())
                .hora(e.getHora())
                .etiqueta(e.getEtiqueta())
                .activo(e.getActivo())
                .build();
    }
}
