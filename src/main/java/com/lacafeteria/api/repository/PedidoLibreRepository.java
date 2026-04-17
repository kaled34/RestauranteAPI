package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.PedidoLibre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoLibreRepository extends JpaRepository<PedidoLibre, Integer> {

    List<PedidoLibre> findByPedido_IdPedido(Integer idPedido);
}
