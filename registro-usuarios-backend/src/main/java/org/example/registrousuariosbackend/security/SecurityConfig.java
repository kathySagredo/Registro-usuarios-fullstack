package org.example.registrousuariosbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Indica que esta clase contiene configuraciones de Spring
@RequiredArgsConstructor // Lombok genera el constructor para las dependencias final
public class SecurityConfig {

    // Un servicio encargado de cargar los usuarios desde la base de datos.
    private final CustomUserDetailsService customUserDetailsService;

    // Filtro encargado de validar el JWT en cada petición.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Configura las reglas de seguridad de la aplicación
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                // Configura CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Deshabilita CSRF para facilitar el desarrollo de una API REST
                .csrf(csrf -> csrf.disable())

                // Indica que NO se utilizarán sesiones HTTP cada petición deberá enviar su JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configura las reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Permite acceder sin autenticación a los endpoints de autenticación
                        .requestMatchers("/auth/**").permitAll()
                        // Cualquier otra ruta requiere autenticación.
                        .anyRequest().authenticated())

                // Agrega nuestro filtro JWT antes del filtro de autenticación de Spring Security
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();

    }


    // Bean encargado de encriptar contraseñas utilizando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    // Bean utilizado posteriormente para autenticar usuarios.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    /*
     * Configura las reglas de CORS (Cross-Origin Resource Sharing).
     * CORS es un mecanismo de seguridad implementado por los navegadores que
     * impide que una aplicación web realice peticiones a un servidor ubicado
     * en un origen distinto (dominio, protocolo o puerto) si el servidor no
     * lo autoriza explícitamente.
     *
     * En este proyecto:
     * Frontend -> http://localhost:5173
     * Backend  -> http://localhost:8080
     *
     * Como ambos utilizan puertos diferentes, el navegador considera que son
     * orígenes distintos, por lo que debemos indicar qué peticiones estarán
     * permitidas.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        // Objeto que almacenará toda la configuración de CORS.
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite que únicamente el frontend desarrollado en React
        // (ejecutándose en localhost:5173) pueda consumir nuestra API.
        // Si en producción el frontend se despliega en otro dominio, este valor deberá modificarse.
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        // Define qué métodos HTTP estarán permitidos.
        // En este proyecto utilizaremos:
        // GET -> Obtener información.
        // POST -> Crear recursos (registro, login).
        // PUT -> Actualizar información.
        // DELETE -> Eliminar recursos.
        // OPTIONS-> Petición automática (preflight) que realiza el navegador
        //           antes de algunas solicitudes para verificar si están permitidas.
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        // Permite que el frontend envíe cualquier encabezado HTTP.
        // Por ejemplo:
        // Content-Type
        // Authorization
        // Accept
        // Esto será especialmente útil cuando enviemos el JWT dentro del encabezado Authorization.
        configuration.setAllowedHeaders(
                List.of("*")
        );

        // Permite enviar credenciales entre frontend y backend.
        // Aunque en este proyecto utilizaremos JWT almacenado en LocalStorage,
        // esta configuración resulta útil si en el futuro se utilizan cookies o autenticación basada en sesiones.
        configuration.setAllowCredentials(true);

        // Crea el objeto encargado de asociar la configuración CORS a las rutas de la aplicación.
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Aplica la configuración definida anteriormente a TODOS los endpoints del backend.
        // "/**" significa cualquier ruta:
        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        // Devuelve la configuración para que Spring Security la utilice en cada petición.
        return source;

    }


}
