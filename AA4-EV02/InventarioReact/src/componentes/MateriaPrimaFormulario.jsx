import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { obtenerPorId, crear, actualizar } from '../servicios/materiaPrimaServicio';

function MateriaPrimaFormulario() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [formulario, setFormulario] = useState({
    nombre: '',
    unidad: '',
    cantidadActual: 0,
    cantidadMinima: 0
  });
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id) {
      obtenerPorId(id)
        .then(mp => setFormulario(mp))
        .catch(() => setError('Registro no encontrado'));
    }
  }, [id]);

  const handleCambio = (e) => {
    const { name, value } = e.target;
    setFormulario(prev => ({
      ...prev,
      [name]: name.startsWith('cantidad') ? Number(value) : value
    }));
  };

  const handleEnviar = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await actualizar(id, formulario);
      } else {
        await crear(formulario);
      }
      navigate('/');
    } catch (err) {
      setError('Error al guardar. Verifique que el servidor esté activo.');
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar' : 'Nueva'} Materia Prima</h2>

      {error && <p className="error">{error}</p>}

      <form onSubmit={handleEnviar} className="formulario">
        <div className="campo">
          <label>Nombre:</label>
          <input
            type="text"
            name="nombre"
            value={formulario.nombre}
            onChange={handleCambio}
            required
            placeholder="Ej: Lamina de acero"
          />
        </div>

        <div className="campo">
          <label>Unidad de medida:</label>
          <input
            type="text"
            name="unidad"
            value={formulario.unidad}
            onChange={handleCambio}
            required
            placeholder="Ej: kg, m2, ml"
          />
        </div>

        <div className="campo">
          <label>Cantidad actual:</label>
          <input
            type="number"
            name="cantidadActual"
            value={formulario.cantidadActual}
            onChange={handleCambio}
            required
            min="0"
          />
        </div>

        <div className="campo">
          <label>Cantidad mínima:</label>
          <input
            type="number"
            name="cantidadMinima"
            value={formulario.cantidadMinima}
            onChange={handleCambio}
            required
            min="0"
          />
        </div>

        <div className="botones">
          <button type="submit" className="btn-guardar">Guardar</button>
          <button type="button" className="btn-cancelar" onClick={() => navigate('/')}>
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}

export default MateriaPrimaFormulario;
