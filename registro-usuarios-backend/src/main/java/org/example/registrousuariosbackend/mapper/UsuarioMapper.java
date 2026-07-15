package org.example.registrousuariosbackend.mapper;

import org.example.registrousuariosbackend.dto.RegisterRequest;
import org.example.registrousuariosbackend.dto.UsuarioResponse;
import org.example.registrousuariosbackend.entity.Rol;
import org.example.registrousuariosbackend.entity.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component // Indica que esta clase será administrada por Spring
public class UsuarioMapper {
    /**
     * Convierte un DTO de registro en una entidad Usuario.
     */
    public static Usuario toEntity(RegisterRequest request) {

        return Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(request.password())
                .rol(Rol.USER)
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    /**
     * Convierte una entidad Usuario en un UsuarioResponse.
     */
    public static UsuarioResponse toResponse(Usuario usuario) {

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol().name(),
                usuario.getFechaRegistro()
                );
    }
}
