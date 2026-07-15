package org.example.registrousuariosbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        // Verifica que el nombre tenga un valor.
        @NotBlank(message = "El nombre es obligatorio.")
        // Verifica que el nombre no supere los 100 caracteres.
        @Size(max = 100)
        String nombre,

        // Verifica que el apellido tenga un valor.
        @NotBlank(message = "El apellido es obligatorio.")
        // Verifica que el apellido no supere los 100 caracteres.
        @Size(max = 100)
        String apellido,

        // Verifica que el correo electrónico tenga un valor.
        @NotBlank(message = "El correo es obligatorio.")
        // Verifica que el valor tenga un formato de correo electrónico válido.
        @Email(message = "Debe ingresar un correo válido.")
        // Verifica que el correo no supere los 255 caracteres.
        @Size(max = 255)
        String email,

        // Verifica que la contraseña tenga un valor.
        @NotBlank(message = "La contraseña es obligatoria.")
        // Verifica que la contraseña tenga entre 8 y 20 caracteres.
        @Size(min = 8, max = 20)
        String password
) {
}
