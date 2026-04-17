package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedido_IdPedido(Integer idPedido);

    void deleteByPedido_IdPedido(Integer idPedido);
}
