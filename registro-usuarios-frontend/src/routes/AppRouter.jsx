// Componentes de React Router
import { Routes, Route } from "react-router-dom";

// Layout principal
import Layout from "../components/Layout";

// Vistas
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Perfil from "../pages/Perfil";
import NotFound from "../pages/NotFound";

function AppRouter() {

    return (

        <Routes>

            {/* Todas estas páginas compartirán el mismo Layout */}
            <Route element={<Layout />}>

                <Route
                    path="/"
                    element={<Home />}
                />

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/perfil"
                    element={<Perfil />}
                />

            </Route>

            {/* Ruta para páginas no existentes */}
            <Route
                path="*"
                element={<NotFound />}
            />

        </Routes>

    );

}

export default AppRouter;