package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Pedido;
import com.lacafeteria.api.entity.enums.EstadoPedido;
import com.lacafeteria.api.entity.enums.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByModulo(Modulo modulo);

    List<Pedido> findByEmpleado_IdEmpleado(Integer idEmpleado);

    List<Pedido> findByCliente_IdCliente(Integer idCliente);

    List<Pedido> findByCreadoEnBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Pedido> findByEstadoAndCreadoEnBetween(EstadoPedido estado,
            LocalDateTime desde,
            LocalDateTime hasta);
}
