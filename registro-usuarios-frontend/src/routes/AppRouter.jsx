// Componentes de React Router
import { Routes, Route } from "react-router-dom";

// Layout principal
import Layout from "../components/Layout";

// Vistas
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import NotFound from "../pages/NotFound";
import Profile from "../pages/Profile";
import ProtectedRoute from "../components/ProtectedRoute";

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
                    element={
                        <ProtectedRoute>
                            <Profile />
                        </ProtectedRoute>
                    }
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