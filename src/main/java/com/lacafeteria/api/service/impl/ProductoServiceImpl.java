package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.ProductoRequest;
import com.lacafeteria.api.dto.response.ProductoResponse;
import com.lacafeteria.api.entity.Categoria;
import com.lacafeteria.api.entity.Producto;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.CategoriaRepository;
import com.lacafeteria.api.repository.ProductoRepository;
import com.lacafeteria.api.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;
    private final CategoriaRepository categoriaRepo;

    @Override
    public List<ProductoResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<ProductoResponse> listarPorCategoria(Integer idCategoria) {
        return repo.findByCategoria_IdCategoria(idCategoria).stream().map(this::toResponse).toList();
    }

    @Override
    public List<ProductoResponse> listarDisponibles() {
        return repo.findByDisponible(true).stream().map(this::toResponse).toList();
    }

    @Override
    public List<ProductoResponse> listarPorCategoriaYDisponibilidad(Integer idCategoria, Boolean disponible) {
        return repo.findByCategoria_IdCategoriaAndDisponible(idCategoria, disponible)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public ProductoResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public ProductoResponse crear(ProductoRequest req) {
        Categoria categoria = findCategoriaOrThrow(req.getIdCategoria());
        Producto entity = Producto.builder()
                .categoria(categoria)
                .nombre(req.getNombre())
                .descripcion(req.getDescripcion())
                .precio(req.getPrecio())
                .disponible(req.getDisponible() != null ? req.getDisponible() : true)
                .imagenUrl(req.getImagenUrl())
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public ProductoResponse actualizar(Integer id, ProductoRequest req) {
        Producto entity = findOrThrow(id);
        Categoria categoria = findCategoriaOrThrow(req.getIdCategoria());
        entity.setCategoria(categoria);
        entity.setNombre(req.getNombre());
        entity.setDescripcion(req.getDescripcion());
        entity.setPrecio(req.getPrecio());
        if (req.getDisponible() != null)
            entity.setDisponible(req.getDisponible());
        entity.setImagenUrl(req.getImagenUrl());
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public ProductoResponse cambiarDisponibilidad(Integer id, Boolean disponible) {
        Producto entity = findOrThrow(id);
        entity.setDisponible(disponible);
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Producto findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id.longValue()));
    }

    private Categoria findCategoriaOrThrow(Integer id) {
        return categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id.longValue()));
    }

    private ProductoResponse toResponse(Producto e) {
        return ProductoResponse.builder()
                .idProducto(e.getIdProducto())
                .idCategoria(e.getCategoria().getIdCategoria())
                .nombreCategoria(e.getCategoria().getNombre())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .precio(e.getPrecio())
                .disponible(e.getDisponible())
                .imagenUrl(e.getImagenUrl())
                .creadoEn(e.getCreadoEn())
                .build();
    }
}
