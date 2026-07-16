package org.example.registrousuariosbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.registrousuariosbackend.dto.LoginRequest;
import org.example.registrousuariosbackend.dto.LoginResponse;
import org.example.registrousuariosbackend.dto.RegisterRequest;
import org.example.registrousuariosbackend.dto.UsuarioResponse;
import org.example.registrousuariosbackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta clase es un controlador REST y devolverá respuestas en formato JSON
@RequestMapping("/auth") // Define la ruta base para todos los endpoints de autenticación
@RequiredArgsConstructor // Lombok genera un constructor con las dependencias final
public class AuthController {

    // Es un servicio que contiene la lógica de negocio para el registro e inicio de sesión
    private final AuthService authService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * URL: POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(
            // @RequestBody convierte el JSON recibido en un objeto RegisterRequest
            // @Valid ejecuta automáticamente las validaciones del DTO
            @Valid @RequestBody RegisterRequest request
    ) {
        // Llama al servicio para registrar el usuario y devuelve una respuesta HTTP 200 (OK)
        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    /**
     * Endpoint para iniciar sesión
     * URL: POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            // @RequestBody convierte el JSON recibido en un objeto LoginRequest
            // @Valid ejecuta automáticamente las validaciones del DTO
            @Valid @RequestBody LoginRequest request
    ) {
        // Llama al servicio para autenticar al usuario y devuelve el token JWT
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
