package org.example.registrousuariosbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

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

    // Obtiene todos los claims almacenados dentro del JWT
    /*
     * Un JWT se divide en Header, Payload, Signature
     * El Payload contiene los claims que son los datos almacenados dentro del token
     * {
     *   "sub": "kathy@email.com",
     *   "iat": 1752750000,
     *   "exp": 1752753600
     * }
    */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                // Indica la clave utilizada para verificar la firma.
                .verifyWith(key)
                // Construye el parser.
                .build()
                // Validación del token y obtiene su contenido.
                .parseSignedClaims(token)
                // Devuelve los claims.
                .getPayload();
    }

    // Permite obtener cualquier información (Claim) almacenada dentro del token.
    // @param claimsResolver Función que indica qué dato extraer.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtiene el email almacenado dentro del token
    public String extractUsername(String token) {
        return extractClaim(
                token,
                Claims::getSubject
        );
    }
    // Obtiene la fecha de expiración almacenada dentro del token
    public Date extractExpiration(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    // Verifica si el token ya expiró
    public boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    // Verifica que el token pertenezca al usuario y que aún no haya expirado
    public boolean isTokenValid(String token, @NonNull String email) {
        String username = extractUsername(token);
        return username.equals(email)
                && !isTokenExpired(token);
    }

}
