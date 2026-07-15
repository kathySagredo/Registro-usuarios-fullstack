package org.example.registrousuariosbackend.dto;

/*
 * DTO utilizado como respuesta al iniciar sesión.
 * Contiene el token JWT que el cliente utilizará para
 * autenticarse en las siguientes solicitudes.
 */
public record LoginResponse(
        // Token JWT generado después de una autenticación exitosa
        String token
) {
}
