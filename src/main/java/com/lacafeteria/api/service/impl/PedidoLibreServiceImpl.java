package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.PedidoLibreRequest;
import com.lacafeteria.api.dto.response.PedidoLibreResponse;
import com.lacafeteria.api.entity.Empleado;
import com.lacafeteria.api.entity.Pedido;
import com.lacafeteria.api.entity.PedidoLibre;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.EmpleadoRepository;
import com.lacafeteria.api.repository.PedidoLibreRepository;
import com.lacafeteria.api.repository.PedidoRepository;
import com.lacafeteria.api.service.PedidoLibreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PedidoLibreServiceImpl implements PedidoLibreService {

    private final PedidoLibreRepository repo;
    private final PedidoRepository pedidoRepo;
    private final EmpleadoRepository empleadoRepo;

    @Override
    public List<PedidoLibreResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<PedidoLibreResponse> listarPorPedido(Integer idPedido) {
        return repo.findByPedido_IdPedido(idPedido).stream().map(this::toResponse).toList();
    }

    @Override
    public PedidoLibreResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public PedidoLibreResponse crear(PedidoLibreRequest req) {
        Pedido pedido = pedidoRepo.findById(req.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", req.getIdPedido().longValue()));
        Empleado admin = empleadoRepo.findById(req.getIdAdmin())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", req.getIdAdmin().longValue()));

        PedidoLibre entity = PedidoLibre.builder()
                .pedido(pedido)
                .descripcion(req.getDescripcion())
                .precioManual(req.getPrecioManual())
                .cantidad(req.getCantidad())
                .admin(admin)
                .notas(req.getNotas())
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public PedidoLibreResponse actualizar(Integer id, PedidoLibreRequest req) {
        PedidoLibre entity = findOrThrow(id);
        Empleado admin = empleadoRepo.findById(req.getIdAdmin())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", req.getIdAdmin().longValue()));
        entity.setDescripcion(req.getDescripcion());
        entity.setPrecioManual(req.getPrecioManual());
        entity.setCantidad(req.getCantidad());
        entity.setAdmin(admin);
        entity.setNotas(req.getNotas());
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private PedidoLibre findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PedidoLibre", id.longValue()));
    }

    private PedidoLibreResponse toResponse(PedidoLibre e) {
        return PedidoLibreResponse.builder()
                .idLibre(e.getIdLibre())
                .idPedido(e.getPedido().getIdPedido())
                .descripcion(e.getDescripcion())
                .precioManual(e.getPrecioManual())
                .cantidad(e.getCantidad())
                .subtotal(e.getSubtotal())
                .idAdmin(e.getAdmin().getIdEmpleado())
                .nombreAdmin(e.getAdmin().getNombre() + " " + e.getAdmin().getApellido())
                .notas(e.getNotas())
                .creadoEn(e.getCreadoEn())
                .build();
    }
}
