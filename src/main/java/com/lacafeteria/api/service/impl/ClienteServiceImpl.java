package com.lacafeteria.api.service.impl;

import com.lacafeteria.api.dto.request.ClienteRequest;
import com.lacafeteria.api.dto.response.ClienteResponse;
import com.lacafeteria.api.dto.response.LoginClienteResponse;
import com.lacafeteria.api.entity.Cliente;
import com.lacafeteria.api.exception.BusinessException;
import com.lacafeteria.api.exception.ResourceNotFoundException;
import com.lacafeteria.api.repository.ClienteRepository;
import com.lacafeteria.api.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<ClienteResponse> listarTodos() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<ClienteResponse> buscarPorTelefono(String telefono) {
        return repo.findByTelefono(telefono).stream().map(this::toResponse).toList();
    }

    @Override
    public ClienteResponse obtenerPorId(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public ClienteResponse obtenerPorEmail(String email) {
        return toResponse(repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente con email " + email + " no encontrado.")));
    }

    /**
     * Verifica email + contraseña y devuelve los datos del cliente.
     * La app Android usa este endpoint para el flujo de login de clientes.
     */
    @Override
    public ClienteResponse loginYObtener(String email, String contrasena) {
        Cliente cliente = repo.findByEmail(email)
                .orElseThrow(() -> new BusinessException(
                        "No existe una cuenta de cliente con el email: " + email));
        if (!passwordEncoder.matches(contrasena, cliente.getContrasena())) {
            throw new BusinessException("Contraseña incorrecta");
        }
        return toResponse(cliente);
    }

    @Override
    public LoginClienteResponse login(String email, String contrasena) {
        Cliente cliente = repo.findByEmail(email)
                .orElseThrow(() -> new BusinessException(
                        "No existe una cuenta de cliente con el email: " + email));
        if (!passwordEncoder.matches(contrasena, cliente.getContrasena())) {
            throw new BusinessException("Contraseña incorrecta");
        }
        return LoginClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .build();
    }

    @Override
    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        if (request.getEmail() != null && repo.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un cliente con el email: " + request.getEmail());
        }
        Cliente entity = Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .build();
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public ClienteResponse actualizar(Integer id, ClienteRequest request) {
        Cliente entity = findOrThrow(id);
        if (request.getEmail() != null
                && !request.getEmail().equals(entity.getEmail())
                && repo.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un cliente con el email: " + request.getEmail());
        }
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setTelefono(request.getTelefono());
        entity.setEmail(request.getEmail());
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            entity.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }
        return toResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        findOrThrow(id);
        repo.deleteById(id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Cliente findOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id.longValue()));
    }

    private ClienteResponse toResponse(Cliente e) {
        return ClienteResponse.builder()
                .idCliente(e.getIdCliente())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .telefono(e.getTelefono())
                .email(e.getEmail())
                .creadoEn(e.getCreadoEn())
                .build();
    }
}