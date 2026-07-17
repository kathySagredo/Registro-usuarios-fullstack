package org.example.registrousuariosbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.registrousuariosbackend.dto.UsuarioResponse;
import org.example.registrousuariosbackend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta clase expone endpoints REST.
@RequestMapping("/usuarios") // Ruta base.
@RequiredArgsConstructor // Lombok genera el constructor.
public class UsuarioController {

    // Contiene la lógica de negocio.
    private final UsuarioService usuarioService;

    // Devuelve la información del usuario autenticado.
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponse> perfil(
            Authentication authentication
    ) {
        // Obtiene el correo del usuario autenticado.
        String email = authentication.getName();
        // Devuelve la información del usuario.
        return ResponseEntity.ok(
                usuarioService.obtenerPerfil(email)
        );
    }

}