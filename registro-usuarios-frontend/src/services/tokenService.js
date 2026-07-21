// Guarda el token JWT.
export function saveToken(token){

    localStorage.setItem(
        "token",
        token
    );

}

// Obtiene el token almacenado.
export function getToken(){

    return localStorage.getItem(
        "token"
    );

}

// Elimina el token.
export function removeToken(){

    localStorage.removeItem(
        "token"
    );

}