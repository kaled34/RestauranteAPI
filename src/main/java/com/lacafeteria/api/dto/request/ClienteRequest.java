package com.lacafeteria.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60)
    private String nombre;

    @Size(max = 60)
    private String apellido;

    @Size(max = 15)
    private String telefono;

    @Email(message = "Email invalido")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
    private String contrasena;
}