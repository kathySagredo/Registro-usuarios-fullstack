package org.example.registrousuariosbackend.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice // Permite capturar excepciones de todos los controladores
public class GlobalExceptionHandler {

    // Maneja errores de tipo BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        // Crea el objeto que será enviado como respuesta al cliente
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // Código HTTP (400)
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // Nombre del estado HTTP
                ex.getMessage(), // Mensaje específico de la excepción
                request.getRequestURI() // Ruta del error
        );
        // Devuelve una respuesta HTTP junto con el objeto ApiError
        return ResponseEntity.badRequest().body(error);
    }

    // Maneja errores de autenticación
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        // Construye la respuesta personalizada del error
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),  // Código HTTP (401)
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), // Nombre del estado HTTP
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // Maneja recursos que no existen
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        // Construye la respuesta personalizada del error
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), // Código HTTP (404)
                HttpStatus.NOT_FOUND.getReasonPhrase(), // Nombre del estado HTTP
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Maneja errores de validación realizada con @valid sobre los DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(@NonNull MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Obtiene el primer mensaje de error generado por las validaciones
        String message = ex.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        // Construye la respuesta personalizada del error
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);

    }

    // Maneja errores producidos por restricciones de validación a nivel entidades
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        // Construye la respuesta personalizada del error
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    // Captura cualquier excepción no controlada
    // Evita que la aplicación devuelva errores sin controlar
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        // Construye la respuesta personalizada del error
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        // Devuelve una respuesta HTTP 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
