package org.example.registrousuariosbackend.repository;

import org.example.registrousuariosbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /*
     * Al extender JpaRepository obtenemos automáticamente métodos como:
     *
     * findAll()      -> Obtiene todos los registros.
     * findById()     -> Busca un registro por su ID.
     * save()         -> Guarda o actualiza un registro.
     * delete()       -> Elimina un registro.
     * count()        -> Cuenta la cantidad de registros.
     * existsById()   -> Verifica si existe un registro por su ID.
     */

    // Busca un usuario por su correo electrónico.
    Optional<Usuario> findByEmail(String email);

    // Verifica si un correo electrónico ya está registrado.
    boolean existsByEmail(String email);

}
