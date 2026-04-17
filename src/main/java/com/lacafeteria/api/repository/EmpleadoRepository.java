package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Empleado;
import com.lacafeteria.api.entity.enums.RolEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    List<Empleado> findByRol(RolEmpleado rol);

    List<Empleado> findByActivo(Boolean activo);

    Optional<Empleado> findByEmail(String email);

    boolean existsByEmail(String email);
}
