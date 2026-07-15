package org.example.registrousuariosbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        // Verifica que el correo no sea nulo, vacío o solo contenga espacios.
        @NotBlank(message="El correo electrónico es obligatorio.")
        // Verifica que el correo tenga un formato de email válido.
        @Email(message="Debe ingresar un correo válido.")
        String email,
        // Verifica que la contraseña no sea nula, vacía o solo contenga espacios.
        @NotBlank(message = "La contraseña es obligatoria.")
        String password
) {}
