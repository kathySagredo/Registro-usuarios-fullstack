package org.example.registrousuariosbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// El filtro se ejecutará una sola vez por cada petición HTTP
/*
* Paso 1: Existe autorización y no está autenticado
* Paso 2: ¿Comienza con Bearer?
* Paso 3: Extraer token
* Paso 4: ¿Es válido?
* Paso 5: Buscar el usuario
* Paso 6: Crear la autenticación
* Paso 7: Guardar la autenticación
* */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Un servicio encargado de generar y validar los JWT
    private final JwtService jwtService;
    // Un servicio encargado de cargar los usuarios desde la base de datos
    private final CustomUserDetailsService userDetailsService;

    // Método principal
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {

        // Obtiene el encabezado Authorization.
        String authHeader = request.getHeader("Authorization");

        // Si no existe el encabezado o no comienza con "Bearer ",
        // continúa con la siguiente etapa del filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Elimina la palabra "Bearer " para obtener únicamente el token
        String jwt = authHeader.substring(7);

        // Obtiene el correo almacenado dentro del JWT
        String email = jwtService.extractUsername(jwt);

        // Si existe un correo y todavía no hay un usuario autenticado
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Busca el usuario en la base de datos.
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            // Verifica que el token sea válido.
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                // Crea un objeto Authentication.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Agrega información adicional de la petición.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Guarda el usuario autenticado en el contexto de seguridad.
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // Continúa con el siguiente filtro.
        filterChain.doFilter(request, response);

    }

}

