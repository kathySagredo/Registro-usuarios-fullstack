package org.example.registrousuariosbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Entity // Indica que esta clase será una entidad JPA (una tabla en la base de datos)
@Table(name = "usuarios") // Define el nombre de la tabla en la base de datos
@Getter // Genera automáticamente los métodos getter de todos los atributos
@Setter // Genera automáticamente los métodos setter de todos los atributos
@NoArgsConstructor // Genera un constructor vacío (requerido por JPA)
@AllArgsConstructor // Genera un constructor con todos los atributos
@Builder // Permite crear objetos de forma más legible
public class Usuario {

    // Identificador único del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del usuario
    @Column(nullable = false, length = 100)
    private String nombre;

    // Apellido del usuario
    @Column(nullable = false, length = 100)
    private String apellido;

    // Correo electrónico (no se puede repetir)
    @Column(nullable = false, unique = true)
    private String email;

    // Contraseña encriptada
    @Column(nullable = false)
    private String password;

    // Rol del usuario
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // Fecha de creación de la cuenta
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

}
