package org.example.registrousuariosbackend.service;

import lombok.RequiredArgsConstructor;
import org.example.registrousuariosbackend.dto.LoginRequest;
import org.example.registrousuariosbackend.dto.LoginResponse;
import org.example.registrousuariosbackend.dto.RegisterRequest;
import org.example.registrousuariosbackend.dto.UsuarioResponse;
import org.example.registrousuariosbackend.entity.Rol;
import org.example.registrousuariosbackend.entity.Usuario;
import org.example.registrousuariosbackend.exception.BadRequestException;
import org.example.registrousuariosbackend.exception.ResourceNotFoundException;
import org.example.registrousuariosbackend.exception.UnauthorizedException;
import org.example.registrousuariosbackend.mapper.UsuarioMapper;
import org.example.registrousuariosbackend.repository.UsuarioRepository;
import org.example.registrousuariosbackend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase contiene la lógica de negocio relacionada con la autenticación
@RequiredArgsConstructor // Lombok genera un constructor con todos los atributos final para inyectar las dependencias
public class AuthService {

    // Permite realizar operaciones sobre la tabla "usuarios"
    private final UsuarioRepository usuarioRepository;
    // Se utiliza para encriptar y verificar contraseñas
    private final PasswordEncoder passwordEncoder;
    // Es un servicio encargado de generar y validar tokens JWT
    private final JwtService jwtService;
    // Convierte entre entidades y DTO
    private final UsuarioMapper usuarioMapper;

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param request Datos enviados por el cliente para crear una cuenta.
     * @return Información del usuario registrado.
     */
    public UsuarioResponse register(RegisterRequest request){
        // Verifica si el correo electrónico ya se encuentra registrado, si existe, se lanza una excepción y el proceso se detiene.
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BadRequestException("El correo electrónico ya se encuentra registrado.");
        }
        // Convierte el DTO recibido en una entidad Usuario
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setRol(Rol.USER); // Por default
        // Encripta la contraseña antes de almacenarla en la base de datos
        usuario.setPassword(passwordEncoder.encode(request.password()));
        // Guarda el usuario en la base de datos
        usuarioRepository.save(usuario);
        // Convierte la entidad en un DTO para enviarla como respuesta
        return usuarioMapper.toResponse(usuario);
    }

    /**
     * Autenticación de un usuario utilizando su correo y contraseña.
     * @param request Credenciales enviadas por el cliente.
     * @return Token JWT que permitirá acceder a las rutas protegidas.
     */
    public LoginResponse login(LoginRequest request){
        // Busca el usuario por su correo electrónico, si no existe, lanza una excepción
        Usuario usuario = usuarioRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        // Compara la contraseña ingresada con la contraseña encriptada almacenada en la base de datos
        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new UnauthorizedException("Credenciales incorrectas.");
        }
        // Genera un token JWT utilizando el correo del usuario
        String token = jwtService.generateToken(usuario.getEmail());
        // Devuelve el token al cliente
        return new LoginResponse(token);
    }
}