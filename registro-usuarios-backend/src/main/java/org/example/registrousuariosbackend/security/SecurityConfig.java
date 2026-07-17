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

@Configuration // Indica que esta clase contiene configuraciones de Spring
@RequiredArgsConstructor // Lombok genera el constructor para las dependencias final
public class SecurityConfig {

    // Un servicio encargado de cargar los usuarios desde la base de datos.
    private final CustomUserDetailsService customUserDetailsService;

    // Filtro encargado de validar el JWT en cada petición.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Configura las reglas de seguridad de la aplicación
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
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

}
