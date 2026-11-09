import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import MateriaPrimaLista from './componentes/MateriaPrimaLista';
import MateriaPrimaFormulario from './componentes/MateriaPrimaFormulario';

function App() {
  return (
    <BrowserRouter>
      <nav className="navbar">
        <span className="nav-titulo">Sistema de Inventario</span>
        <Link to="/" className="nav-link">Lista</Link>
        <Link to="/nuevo" className="nav-link">+ Nueva Materia Prima</Link>
      </nav>

      <div className="contenido">
        <Routes>
          <Route path="/" element={<MateriaPrimaLista />} />
          <Route path="/nuevo" element={<MateriaPrimaFormulario />} />
          <Route path="/editar/:id" element={<MateriaPrimaFormulario />} />
        </Routes>
      </div>

      <footer className="footer">
        <p>GA7-220501096-AA4-EV02 | Diego Andrés Fonseca</p>
      </footer>
    </BrowserRouter>
  );
}

export default App;
