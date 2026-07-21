// Permite administrar el estado del formulario.
import { useState } from "react";
// Permite navegar entre páginas.
import { useNavigate } from "react-router-dom";
// Función encargada de registrar usuarios.
import { register } from "../services/authService";

function Register() {
    // Permite cambiar de página después del registro.
    const navigate = useNavigate();
    // Estado del formulario - valor inicial campos vacios
    const [formData, setFormData] = useState({
        nombre: "",
        apellido: "",
        email: "",
        password: ""
    });

    // Mensaje de error.
    const [error, setError] = useState("");
    // Mensaje de éxito.
    const [success, setSuccess] = useState("");

    // Actualiza el campo correspondiente cada vez que el usuario escribe.
    function handleChange(event) {
        // Toma el nombre del campo y su valor que corresponde a formData.name inicial
        const { name, value } = event.target;
        // Actualiza únicamente la propiedad correspondiente manteniendo el resto de la información del formulario.
        setFormData({...formData, [name]: value});
    }

    // Envía la información al backend.
    async function handleSubmit(event) {
        // Evita que el formulario recargue la página.
        event.preventDefault();
        // Limpia mensajes anteriores.
        setError("");
        setSuccess("");
        try {
            // Envía los datos al backend.
            await register(formData);
            // Mensaje de éxito.
            setSuccess("Usuario registrado correctamente.");
            // Limpia el formulario.
            setFormData({
                nombre: "",
                apellido: "",
                email: "",
                password: ""
            });
            // Espera 2 segundos y redirige al login.
            setTimeout(() => {
                navigate("/login");
            }, 2000);

        } catch (error) {
            // Muestra el mensaje enviado por el backend.
            setError(error.message);
        }
    }

    return (
        <div className="row justify-content-center">
            <div className="col-md-6">
                <h2 className="mb-4">Crear cuenta</h2>
                {success &&
                    <div className="alert alert-success">
                        {success}
                    </div>
                }
                {error &&
                    <div className="alert alert-danger">
                        {error}
                    </div>
                }
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label className="form-label">Nombre</label>
                        <input
                            className="form-control"
                            type="text"
                            name="nombre"
                            value={formData.nombre}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Apellido</label>
                        <input
                            className="form-control"
                            type="text"
                            name="apellido"
                            value={formData.apellido}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Correo electrónico</label>
                        <input
                            className="form-control"
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-4">
                        <label className="form-label">Contraseña</label>
                        <input
                            className="form-control"
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </div>
                    <button className="btn btn-primary w-100" type="submit">Registrarse</button>
                </form>
            </div>
        </div>
    );
}

export default Register;