import { Navigate } from "react-router-dom";
import { getToken } from "../services/tokenService";

/*
 * Componente encargado de proteger rutas privadas.
 * Si existe un JWT, muestra el componente solicitado.
 * En caso contrario, redirige al usuario al login.
 */

function ProtectedRoute({ children }) {

    // Obtiene el token almacenado.
    const token = getToken();
    // Si no existe token, redirige al login y evita que vuelva atrás.
    if (!token) {
        return <Navigate to="/login" replace />;
    }

    // Si existe token, permite acceder a la ruta.
    return children;
}

export default ProtectedRoute;