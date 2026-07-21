import { useEffect, useState } from "react";
import { getProfile } from "../services/authService";

function Profile() {
    // Información del usuario
    const [usuario, setUsuario] = useState(null);
    // Error
    const [error, setError] = useState("");
    // Indica si la información aún se está cargando
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function cargarPerfil() {
            try {
                const respuesta = await getProfile();
                setUsuario(respuesta);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        }
        cargarPerfil();
    }, []); // Arreglo vacio, una vez

    if (loading) {
        return <p>Cargando información...</p>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2> Mi perfil</h2>
            <p><strong>Nombre:</strong> {usuario.nombre}</p>
            <p><strong>Apellido:</strong> {usuario.apellido}</p>
            <p><strong>Email:</strong> {usuario.email}</p>
            <p><strong>Rol:</strong> {usuario.rol}</p>
        </div>
    );
}

export default Profile;