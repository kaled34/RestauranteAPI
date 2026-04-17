package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.EmpleadoRequest;
import com.lacafeteria.api.dto.response.EmpleadoResponse;
import com.lacafeteria.api.dto.response.LoginEmpleadoResponse;
import com.lacafeteria.api.entity.Empleado;
import com.lacafeteria.api.entity.enums.RolEmpleado;
import com.lacafeteria.api.exception.BusinessException;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.EmpleadoRepository;
import com.lacafeteria.api.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<EmpleadoResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<EmpleadoResponse> listarPorRol(RolEmpleado rol) {
        return repo.findByRol(rol).stream().map(this::toResponse).toList();
    }

    @Override
    public List<EmpleadoResponse> listarActivos() {
        return repo.findByActivo(true).stream().map(this::toResponse).toList();
    }

    @Override
    public EmpleadoResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public EmpleadoResponse crear(EmpleadoRequest req) {
        if (req.getEmail() != null && repo.existsByEmail(req.getEmail())) {
            throw new BusinessException("Ya existe un empleado con el email: " + req.getEmail());
        }
        Empleado entity = Empleado.builder()
                .nombre(req.getNombre())
                .apellido(req.getApellido())
                .rol(req.getRol())
                .telefono(req.getTelefono())
                .email(req.getEmail())
                .contrasena(passwordEncoder.encode(req.getContrasena()))
                .activo(req.getActivo() != null ? req.getActivo() : true)
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public EmpleadoResponse actualizar(Integer id, EmpleadoRequest req) {
        Empleado entity = findOrThrow(id);
        if (req.getEmail() != null
                && !req.getEmail().equals(entity.getEmail())
                && repo.existsByEmail(req.getEmail())) {
            throw new BusinessException("Ya existe un empleado con el email: " + req.getEmail());
        }
        entity.setNombre(req.getNombre());
        entity.setApellido(req.getApellido());
        entity.setRol(req.getRol());
        entity.setTelefono(req.getTelefono());
        entity.setEmail(req.getEmail());
        if (req.getContrasena() != null && !req.getContrasena().isBlank()) {
            entity.setContrasena(passwordEncoder.encode(req.getContrasena()));
        }
        if (req.getActivo() != null)
            entity.setActivo(req.getActivo());
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public EmpleadoResponse cambiarActivo(Integer id, Boolean activo) {
        Empleado entity = findOrThrow(id);
        entity.setActivo(activo);
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Empleado findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", id.longValue()));
    }

    private EmpleadoResponse toResponse(Empleado e) {
        return EmpleadoResponse.builder()
                .idEmpleado(e.getIdEmpleado())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .rol(e.getRol())
                .telefono(e.getTelefono())
                .email(e.getEmail())
                .activo(e.getActivo())
                .creadoEn(e.getCreadoEn())
                .build();
    }

    @Override
    public LoginEmpleadoResponse login(String email, String contrasena) {
        Empleado empleado = repo.findByEmail(email)
                .orElseThrow(() -> new BusinessException(
                        "No existe una cuenta con el email: " + email));
        if (!empleado.getActivo()) {
            throw new BusinessException("Esta cuenta está desactivada.");
        }
        if (!passwordEncoder.matches(contrasena, empleado.getContrasena())) {
            throw new BusinessException("Contraseña incorrecta");
        }
        return LoginEmpleadoResponse.builder()
                .idEmpleado(empleado.getIdEmpleado())
                .nombre(empleado.getNombre())
                .apellido(empleado.getApellido())
                .rol(empleado.getRol())
                .email(empleado.getEmail())
                .build();
    }
}
