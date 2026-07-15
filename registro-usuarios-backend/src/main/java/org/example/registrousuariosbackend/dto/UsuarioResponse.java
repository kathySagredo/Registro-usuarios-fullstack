package org.example.registrousuariosbackend.dto;

import java.time.LocalDateTime;

/*
 * DTO utilizado para enviar la información del usuario al cliente.
 * A diferencia de la entidad Usuario, este DTO no incluye la contraseña,
 * evitando exponer información sensible.
 */

public record UsuarioResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        String rol,
        LocalDateTime fechaRegistro
) {}
