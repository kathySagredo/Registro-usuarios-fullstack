// Punto de entrada de la aplicación React.

// Permite renderizar la aplicación en el navegador.
import { StrictMode } from "react";

// Crea el punto de montaje de React sobre el DOM.
import { createRoot } from "react-dom/client";

// Importa Bootstrap para utilizar sus estilos.
import "bootstrap/dist/css/bootstrap.min.css";

// Importa los estilos globales de la aplicación.
import "./index.css";

// Componente principal.
import App from "./App.jsx";

// Renderiza la aplicación dentro del elemento con id="root".
createRoot(document.getElementById("root")).render(
    <StrictMode>
        <App />
    </StrictMode>
);
