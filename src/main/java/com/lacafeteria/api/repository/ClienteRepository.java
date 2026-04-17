package com.lacafeteria.api.repository;

import com.lacafeteria.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByTelefono(String telefono);

    boolean existsByEmail(String email);
}
