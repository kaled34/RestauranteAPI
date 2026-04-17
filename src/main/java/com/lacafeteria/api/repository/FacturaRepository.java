package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Factura;
import com.lacafeteria.api.entity.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    Optional<Factura> findByPedido_IdPedido(Integer idPedido);

    List<Factura> findByPagado(Boolean pagado);

    List<Factura> findByMetodoPago(MetodoPago metodoPago);

    List<Factura> findByEmitidaEnBetween(LocalDateTime desde, LocalDateTime hasta);

    boolean existsByPedido_IdPedido(Integer idPedido);
}
