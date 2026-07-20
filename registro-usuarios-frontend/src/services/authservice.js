// Función reutilizable para comunicarse con el backend.
import { apiFetch } from "./api";

/*
 * Envía una solicitud para registrar un nuevo usuario.
 * @param {object} usuario Datos del formulario.
 */
export async function register(usuario) {
    return apiFetch("/auth/register", {
        method: "POST",
        body: JSON.stringify(usuario)
    });
}

/*
 * Envía las credenciales para iniciar sesión.
 * @param {object} credenciales
 */
export async function login(credenciales) {
    return apiFetch("/auth/login", {
        method: "POST",
        body: JSON.stringify(credenciales)
    });
}