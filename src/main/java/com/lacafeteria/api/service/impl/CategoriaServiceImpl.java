package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.CategoriaRequest;
import com.lacafeteria.api.dto.response.CategoriaResponse;
import com.lacafeteria.api.entity.Categoria;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.exception.BusinessException;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.CategoriaRepository;
import com.lacafeteria.api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repo;

    @Override
    public List<CategoriaResponse> listarTodas() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<CategoriaResponse> listarPorModulo(Modulo modulo) {
        return repo.findByModulo(modulo).stream().map(this::toResponse).toList();
    }

    @Override
    public List<CategoriaResponse> listarActivas() {
        return repo.findByActivo(true).stream().map(this::toResponse).toList();
    }

    @Override
    public CategoriaResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public CategoriaResponse crear(CategoriaRequest req) {
        if (repo.existsByNombre(req.getNombre())) {
            throw new BusinessException("Ya existe una categoria con el nombre: " + req.getNombre());
        }
        Categoria entity = Categoria.builder()
                .nombre(req.getNombre())
                .descripcion(req.getDescripcion())
                .modulo(req.getModulo())
                .activo(req.getActivo() != null ? req.getActivo() : true)
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public CategoriaResponse actualizar(Integer id, CategoriaRequest req) {
        Categoria entity = findOrThrow(id);
        if (!entity.getNombre().equals(req.getNombre()) && repo.existsByNombre(req.getNombre())) {
            throw new BusinessException("Ya existe una categoria con el nombre: " + req.getNombre());
        }
        entity.setNombre(req.getNombre());
        entity.setDescripcion(req.getDescripcion());
        entity.setModulo(req.getModulo());
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

    private Categoria findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id.longValue()));
    }

    private CategoriaResponse toResponse(Categoria e) {
        return CategoriaResponse.builder()
                .idCategoria(e.getIdCategoria())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .modulo(e.getModulo())
                .activo(e.getActivo())
                .creadoEn(e.getCreadoEn())
                .build();
    }
}
