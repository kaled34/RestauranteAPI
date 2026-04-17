package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByCategoria_IdCategoria(Integer idCategoria);

    List<Producto> findByDisponible(Boolean disponible);

    List<Producto> findByCategoria_IdCategoriaAndDisponible(Integer idCategoria, Boolean disponible);
}
