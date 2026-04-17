package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.HorarioDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioDisponibleRepository extends JpaRepository<HorarioDisponible, Integer> {

    List<HorarioDisponible> findByActivo(Boolean activo);
}
