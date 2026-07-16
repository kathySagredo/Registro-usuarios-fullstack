package org.example.registrousuariosbackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service // Indica que esta clase es un componente de Spring encargado de la lógica relacionada con JWT
public class JwtService {

    // Guarda la clave criptográfica utilizada para firmar y validar los tokens JWT
    private final SecretKey key;
    // Tiempo de duración del token JWT antes de que expire
    private final long expiration;

    /*
     * Constructor donde Spring inyecta los valores configurados
     * en application.yml:
     *
     * jwt:
     *   secret: ${JWT_SECRET}
     *   expiration: ${JWT_EXPIRATION}
     *
     */
    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {

        // Convierte la clave secreta almacenada en formato Base64
        // Crea una clave criptográfica utilizando el algoritmo HMAC
        // Esta clave será utilizada para firmar y verificar los JWT.
        this.key = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
        // Guarda el tiempo de expiración configurado para utilizarlo
        // cuando se genere un nuevo token.
        this.expiration = expiration;
    }

    // Genera un token JWT utilizando el email del usuario.
    public String generateToken(String email) {
        return Jwts.builder()
                // Información que guardamos dentro del token
                .subject(email)
                // Fecha creación
                .issuedAt(new Date())
                // Fecha expiración
                .expiration(
                        new Date(System.currentTimeMillis() + expiration)
                )
                // Firma digital del token
                .signWith(key)
                .compact();
    }

    // Obtiene el email almacenado dentro del token
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
