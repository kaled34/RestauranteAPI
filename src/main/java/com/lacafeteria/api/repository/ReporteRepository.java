package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Reporte;
import com.lacafeteria.api.entity.enums.TipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByTipo(TipoReporte tipo);

    List<Reporte> findByGeneradoPor_IdEmpleado(Integer idEmpleado);

    List<Reporte> findByFechaInicioBetween(LocalDate desde, LocalDate hasta);
}
