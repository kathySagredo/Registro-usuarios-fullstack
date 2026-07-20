// Permite mostrar la página correspondiente según la ruta.
import { Outlet } from "react-router-dom";
// Barra de navegación.
import Navbar from "./Navbar";

function Layout() {

    return (
        <>
            {/* Barra de navegación */}
            <Navbar />
            {/* Aquí React Router renderiza la página correspondiente */}
            <main className="container mt-4">
                <Outlet />
            </main>
        </>
    );
}

export default Layout;