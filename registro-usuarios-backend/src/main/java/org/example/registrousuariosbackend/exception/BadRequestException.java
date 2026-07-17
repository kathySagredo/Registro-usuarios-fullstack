package org.example.registrousuariosbackend.exception;

//Excepción utilizada cuando la solicitud enviada por el cliente contiene información inválida
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
