package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Categoria;
import com.lacafeteria.api.entity.enums.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByModulo(Modulo modulo);

    List<Categoria> findByActivo(Boolean activo);

    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
