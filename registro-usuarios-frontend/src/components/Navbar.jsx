// Permite navegar entre las distintas páginas sin recargar el navegador.
import { NavLink } from "react-router-dom";

function Navbar() {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container">
                <NavLink className="navbar-brand" to="/">RegistroUsuarios</NavLink>
                <div className="navbar-nav">
                    <NavLink className="nav-link" to="/">Home</NavLink>
                    <NavLink className="nav-link" to="/register">Registrarse</NavLink>
                    <NavLink className="nav-link" to="/login">Iniciar sesión</NavLink>
                    <NavLink className="nav-link" to="/perfil">Perfil</NavLink>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;