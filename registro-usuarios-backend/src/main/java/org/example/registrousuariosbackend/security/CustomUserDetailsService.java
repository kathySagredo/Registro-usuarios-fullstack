package org.example.registrousuariosbackend.security;

import lombok.NonNull;
import org.example.registrousuariosbackend.entity.Usuario;
import org.example.registrousuariosbackend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un servicio administrado por Spring.
public class CustomUserDetailsService implements UserDetailsService {

    // Repositorio utilizado para consultar los usuarios almacenados en la base de datos.
    private final UsuarioRepository usuarioRepository;

    // Constructor para inyectar el repositorio.
    public CustomUserDetailsService(
            UsuarioRepository usuarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método utilizado por Spring Security para buscar un usuario durante el proceso de autenticación.
     * @param email Correo electrónico ingresado por el usuario.
     * @return Objeto UserDetails con la información necesaria para autenticar al usuario.
     * @throws UsernameNotFoundException Si el usuario no existe.
     */
    @Override
    @NonNull // este método no acepta parámetros nulos y garantiza que tampoco devolverá un valor nulo
    public UserDetails loadUserByUsername(@NonNull String email)
            throws UsernameNotFoundException {
        // Busca el usuario por su correo electrónico, si no existe, lanza una excepción
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));
        // Convierte nuestra entidad Usuario en un objeto UserDetails
        return User.builder()
                // Correo electrónico que identifica al usuario
                .username(usuario.getEmail())
                // Contraseña encriptada almacenada en la base de datos
                .password(usuario.getPassword())
                // Rol o autoridad asignada al usuario
                .roles(usuario.getRol().name())
                // Construye y devuelve el objeto UserDetails
                .build();
    }

}
