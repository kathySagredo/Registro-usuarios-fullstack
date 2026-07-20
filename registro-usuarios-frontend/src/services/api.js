// Dirección base donde se encuentra ejecutándose el backend.
const BASE_URL = "http://localhost:8080";

/*
 * Función reutilizable para realizar peticiones HTTP al backend.
 *
 * @param {string} endpoint Ruta del endpoint.
 * @param {object} options Configuración adicional de fetch.
 * @returns {Promise<any>} Datos devueltos por la API.
 */
export async function apiFetch(endpoint, options = {}) {

    // Une la URL base con el endpoint solicitado.
    const url = `${BASE_URL}${endpoint}`;

    // Configuración por defecto.
    const config = {

        // Cabeceras enviadas al backend.
        headers: {
            "Content-Type": "application/json",
            ...options.headers
        },

        // Permite sobrescribir cualquier configuración.
        ...options

    };

    // Realiza la petición HTTP.
    const response = await fetch(url, config);

    /*
     response.ok será true únicamente
     cuando la respuesta tenga un código
     entre 200 y 299.
    */

    if (!response.ok) {

        let errorMessage = "Ha ocurrido un error.";

        try {

            // Intenta leer el mensaje enviado por el backend.
            const error = await response.json();

            errorMessage = error.message;

        } catch {

            // Si el backend no devuelve JSON,
            // se mantiene el mensaje por defecto.
        }

        throw new Error(errorMessage);

    }

    /*
      Si la petición fue exitosa,
      convierte el JSON recibido
      en un objeto JavaScript.
    */

    return response.json();

}