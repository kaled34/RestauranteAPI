package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.DetallePedidoRequest;
import com.lacafeteria.api.dto.request.PedidoRequest;
import com.lacafeteria.api.dto.response.DetallePedidoResponse;
import com.lacafeteria.api.dto.response.PedidoResponse;
import com.lacafeteria.api.entity.*;
import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;
import com.lacafeteria.api.exception.BusinessException;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.*;
import com.lacafeteria.api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepo;
    private final EmpleadoRepository empleadoRepo;
    private final ClienteRepository clienteRepo;
    private final HorarioDisponibleRepository horarioRepo;
    private final ProductoRepository productoRepo;
    private final DetallePedidoRepository detalleRepo;

    @Override
    public List<PedidoResponse> listarTodos() {
        return pedidoRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<PedidoResponse> listarPorEstado(EstadoPedido estado) {
        return pedidoRepo.findByEstado(estado).stream().map(this::toResponse).toList();
    }

    @Override
    public List<PedidoResponse> listarPorModulo(Modulo modulo) {
        return pedidoRepo.findByModulo(modulo).stream().map(this::toResponse).toList();
    }

    @Override
    public List<PedidoResponse> listarPorEmpleado(Integer idEmpleado) {
        return pedidoRepo.findByEmpleado_IdEmpleado(idEmpleado).stream().map(this::toResponse).toList();
    }

    @Override
    public List<PedidoResponse> listarPorCliente(Integer idCliente) {
        return pedidoRepo.findByCliente_IdCliente(idCliente).stream().map(this::toResponse).toList();
    }

    @Override
    public List<PedidoResponse> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return pedidoRepo.findByCreadoEnBetween(desde, hasta).stream().map(this::toResponse).toList();
    }

    @Override
    public PedidoResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public PedidoResponse crear(PedidoRequest req) {
        Empleado empleado = empleadoRepo.findById(req.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", req.getIdEmpleado().longValue()));

        Cliente cliente = null;
        if (req.getIdCliente() != null) {
            cliente = clienteRepo.findById(req.getIdCliente())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente", req.getIdCliente().longValue()));
        }

        HorarioDisponible horario = null;
        if (req.getIdHorarioRecogida() != null) {
            horario = horarioRepo.findById(req.getIdHorarioRecogida())
                    .orElseThrow(() -> new ResourceNotFoundException("HorarioDisponible",
                            req.getIdHorarioRecogida().longValue()));
        }

        Pedido pedido = Pedido.builder()
                .empleado(empleado)
                .cliente(cliente)
                .modulo(req.getModulo())
                .estado(req.getEstado() != null ? req.getEstado() : EstadoPedido.pendiente)
                .tipo(req.getTipo())
                .horarioRecogida(horario)
                .notas(req.getNotas())
                .build();

        pedidoRepo.save(pedido);

        // Guardar detalles si vienen en el request
        if (req.getDetalles() != null && !req.getDetalles().isEmpty()) {
            for (DetallePedidoRequest detReq : req.getDetalles()) {
                Producto producto = productoRepo.findById(detReq.getIdProducto())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Producto", detReq.getIdProducto().longValue()));
                if (!producto.getDisponible()) {
                    throw new BusinessException("El producto '" + producto.getNombre() + "' no esta disponible.");
                }
                DetallePedido detalle = DetallePedido.builder()
                        .pedido(pedido)
                        .producto(producto)
                        .cantidad(detReq.getCantidad())
                        .precioUnitario(detReq.getPrecioUnitario())
                        .notas(detReq.getNotas())
                        .build();
                detalleRepo.save(detalle);
            }
        }

        return toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse cambiarEstado(Integer id, EstadoPedido estado) {
        Pedido pedido = findOrThrow(id);
        if (pedido.getEstado() == EstadoPedido.cancelado) {
            throw new BusinessException("No se puede cambiar el estado de un pedido cancelado.");
        }
        pedido.setEstado(estado);
        return toResponse(pedidoRepo.save(pedido));
    }

    @Override
    @Transactional
    public void cancelar(Integer id) {
        Pedido pedido = findOrThrow(id);
        if (pedido.getEstado() == EstadoPedido.entregado) {
            throw new BusinessException("No se puede cancelar un pedido ya entregado.");
        }
        pedido.setEstado(EstadoPedido.cancelado);
        pedidoRepo.save(pedido);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Pedido findOrThrow(Integer id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id.longValue()));
    }

    private PedidoResponse toResponse(Pedido e) {
        List<DetallePedidoResponse> detalles = detalleRepo.findByPedido_IdPedido(e.getIdPedido())
                .stream().map(d -> DetallePedidoResponse.builder()
                        .idDetalle(d.getIdDetalle())
                        .idProducto(d.getProducto().getIdProducto())
                        .nombreProducto(d.getProducto().getNombre())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getSubtotal())
                        .notas(d.getNotas())
                        .build())
                .toList();

        return PedidoResponse.builder()
                .idPedido(e.getIdPedido())
                .idEmpleado(e.getEmpleado().getIdEmpleado())
                .nombreEmpleado(e.getEmpleado().getNombre() + " " + e.getEmpleado().getApellido())
                .idCliente(e.getCliente() != null ? e.getCliente().getIdCliente() : null)
                .nombreCliente(e.getCliente() != null ? e.getCliente().getNombre() : null)
                .modulo(e.getModulo())
                .estado(e.getEstado())
                .tipo(e.getTipo())
                .idHorarioRecogida(e.getHorarioRecogida() != null ? e.getHorarioRecogida().getIdHorario() : null)
                .etiquetaHorario(e.getHorarioRecogida() != null ? e.getHorarioRecogida().getEtiqueta() : null)
                .notas(e.getNotas())
                .creadoEn(e.getCreadoEn())
                .actualizadoEn(e.getActualizadoEn())
                .detalles(detalles)
                .build();
    }
}
