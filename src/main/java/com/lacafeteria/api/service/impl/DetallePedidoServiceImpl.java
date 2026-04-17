package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.DetallePedidoRequest;
import com.lacafeteria.api.dto.response.DetallePedidoResponse;
import com.lacafeteria.api.entity.DetallePedido;
import com.lacafeteria.api.entity.Pedido;
import com.lacafeteria.api.entity.Producto;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.DetallePedidoRepository;
import com.lacafeteria.api.repository.PedidoRepository;
import com.lacafeteria.api.repository.ProductoRepository;
import com.lacafeteria.api.service.DetallePedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository repo;
    private final PedidoRepository pedidoRepo;
    private final ProductoRepository productoRepo;

    @Override
    public List<DetallePedidoResponse> listarPorPedido(Integer idPedido) {
        return repo.findByPedido_IdPedido(idPedido).stream().map(this::toResponse).toList();
    }

    @Override
    public DetallePedidoResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public DetallePedidoResponse agregar(DetallePedidoRequest req, Integer idPedido) {
        Pedido pedido = pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", idPedido.longValue()));
        Producto producto = productoRepo.findById(req.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", req.getIdProducto().longValue()));

        DetallePedido entity = DetallePedido.builder()
                .pedido(pedido)
                .producto(producto)
                .cantidad(req.getCantidad())
                .precioUnitario(req.getPrecioUnitario())
                .notas(req.getNotas())
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public DetallePedidoResponse actualizar(Integer id, DetallePedidoRequest req) {
        DetallePedido entity = findOrThrow(id);
        Producto producto = productoRepo.findById(req.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", req.getIdProducto().longValue()));
        entity.setProducto(producto);
        entity.setCantidad(req.getCantidad());
        entity.setPrecioUnitario(req.getPrecioUnitario());
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

    private DetallePedido findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePedido", id.longValue()));
    }

    private DetallePedidoResponse toResponse(DetallePedido e) {
        return DetallePedidoResponse.builder()
                .idDetalle(e.getIdDetalle())
                .idProducto(e.getProducto().getIdProducto())
                .nombreProducto(e.getProducto().getNombre())
                .cantidad(e.getCantidad())
                .precioUnitario(e.getPrecioUnitario())
                .subtotal(e.getSubtotal())
                .notas(e.getNotas())
                .build();
    }
}
