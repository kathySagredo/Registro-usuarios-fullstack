import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";
import { saveToken } from "../services/tokenService";

function Login() {

    const navigate = useNavigate();

    // Estado del formulario
    const [formData, setFormData] = useState({
        email: "",
        password: ""
    });

    // Mensaje de error
    const [error, setError] = useState("");

    // Se ejecuta cuando el usuario escribe
    function handleChange(event) {
        const nombreCampo = event.target.name;
        const valorCampo = event.target.value;
        setFormData({
            ...formData,
            [nombreCampo]: valorCampo
        });

    }

    // Enviar formulario
    async function handleSubmit(event) {
        event.preventDefault();
        setError("");
        try {
            const respuesta = await login(formData);

            // Guarda el JWT utilizando la clave "token".
            saveToken(
                respuesta.token
            );

            // Redirecciona al perfil.
            navigate("/perfil");
        } catch (error) {
            setError(error.message);
        }
    }

    return (
        <div className="row justify-content-center">
            <div className="col-md-5">
                <h2 className="mb-4">Iniciar sesión</h2>
                {error &&
                    <div className="alert alert-danger">
                        {error}
                    </div>
                }
                <form onSubmit={handleSubmit}>
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
                    <button className="btn btn-primary w-100" type="submit">Iniciar sesión</button>
                </form>
            </div>
        </div>
    );
}

export default Login;