package org.example.registrousuariosbackend.exception;

// Excepción utilizada cuando un usuario intenta autenticarse con credenciales incorrectas
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}