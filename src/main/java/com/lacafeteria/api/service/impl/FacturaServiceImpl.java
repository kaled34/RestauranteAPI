package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.FacturaRequest;
import com.lacafeteria.api.dto.response.FacturaResponse;
import com.lacafeteria.api.entity.Factura;
import com.lacafeteria.api.entity.Pedido;
import com.lacafeteria.api.entity.enums.MetodoPago;
import com.lacafeteria.api.exception.BusinessException;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.FacturaRepository;
import com.lacafeteria.api.repository.PedidoRepository;
import com.lacafeteria.api.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository repo;
    private final PedidoRepository pedidoRepo;

    @Override
    public List<FacturaResponse> listarTodas() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<FacturaResponse> listarPorPagado(Boolean pagado) {
        return repo.findByPagado(pagado).stream().map(this::toResponse).toList();
    }

    @Override
    public List<FacturaResponse> listarPorMetodoPago(MetodoPago metodoPago) {
        return repo.findByMetodoPago(metodoPago).stream().map(this::toResponse).toList();
    }

    @Override
    public List<FacturaResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return repo.findByEmitidaEnBetween(desde, hasta).stream().map(this::toResponse).toList();
    }

    @Override
    public FacturaResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public FacturaResponse obtenerPorPedido(Integer idPedido) {
        return toResponse(repo.findByPedido_IdPedido(idPedido)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Factura para pedido " + idPedido + " no encontrada.")));
    }

    @Override
    @Transactional
    public FacturaResponse emitir(FacturaRequest req) {
        if (repo.existsByPedido_IdPedido(req.getIdPedido())) {
            throw new BusinessException("El pedido " + req.getIdPedido() + " ya tiene una factura emitida.");
        }
        Pedido pedido = pedidoRepo.findById(req.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", req.getIdPedido().longValue()));

        Factura entity = Factura.builder()
                .pedido(pedido)
                .subtotal(req.getSubtotal())
                .descuento(req.getDescuento() != null ? req.getDescuento() : java.math.BigDecimal.ZERO)
                .impuesto(req.getImpuesto() != null ? req.getImpuesto() : java.math.BigDecimal.ZERO)
                .total(req.getTotal())
                .metodoPago(req.getMetodoPago())
                .pagado(false)
                .referenciaPago(req.getReferenciaPago())
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public FacturaResponse marcarComoPagada(Integer id, String referenciaPago) {
        Factura entity = findOrThrow(id);
        if (entity.getPagado()) {
            throw new BusinessException("La factura " + id + " ya esta marcada como pagada.");
        }
        entity.setPagado(true);
        if (referenciaPago != null && !referenciaPago.isBlank()) {
            entity.setReferenciaPago(referenciaPago);
        }
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Factura findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura", id.longValue()));
    }

    private FacturaResponse toResponse(Factura e) {
        return FacturaResponse.builder()
                .idFactura(e.getIdFactura())
                .idPedido(e.getPedido().getIdPedido())
                .subtotal(e.getSubtotal())
                .descuento(e.getDescuento())
                .impuesto(e.getImpuesto())
                .total(e.getTotal())
                .metodoPago(e.getMetodoPago())
                .pagado(e.getPagado())
                .referenciaPago(e.getReferenciaPago())
                .emitidaEn(e.getEmitidaEn())
                .build();
    }
}
