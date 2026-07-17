package org.example.registrousuariosbackend.service;

import lombok.RequiredArgsConstructor;
import org.example.registrousuariosbackend.dto.UsuarioResponse;
import org.example.registrousuariosbackend.entity.Usuario;
import org.example.registrousuariosbackend.exception.ResourceNotFoundException;
import org.example.registrousuariosbackend.mapper.UsuarioMapper;
import org.example.registrousuariosbackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service // Contiene la lógica de negocio relacionada con los usuarios.
@RequiredArgsConstructor // Lombok genera el constructor para las dependencias final.
public class UsuarioService {

    // Permite acceder a la tabla de "usuarios"
    private final UsuarioRepository usuarioRepository;

    // Convierte entidades en DTO.
    private final UsuarioMapper usuarioMapper;

/*
 * Obtiene la información del usuario
 * utilizando su correo electrónico.
 *
 * @param email Correo del usuario autenticado.
 * @return Información del usuario.
 */
public UsuarioResponse obtenerPerfil(String email) {

    // Busca el usuario por su correo.
    Usuario usuario = usuarioRepository
            .findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Usuario no encontrado."
                    )
            );

    // Convierte la entidad en DTO.
    return usuarioMapper.toResponse(usuario);
}
}