// MateriaPrimaLista.jsx - Muestra la tabla de materias primas
// Autor: Diego Andrés Fonseca
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { obtenerTodas, eliminar } from '../servicios/materiaPrimaServicio';

function MateriaPrimaLista() {
  // Estado: lista de materias primas
  const [materias, setMaterias] = useState([]);
  // Estado: mensaje de error si la carga falla
  const [error, setError] = useState(null);

  // Cargar datos al montar el componente
  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const datos = await obtenerTodas();
      setMaterias(datos);
    } catch (e) {
      setError('No se pudieron cargar los datos. Verifique que el servidor esté en ejecución.');
    }
  };

  const handleEliminar = async (id) => {
    if (!window.confirm('¿Desea eliminar este registro?')) return;
    try {
      await eliminar(id);
      // Actualizar la lista sin recargar la pagina
      setMaterias(materias.filter(m => m.id !== id));
    } catch (e) {
      alert('Error al eliminar');
    }
  };

  if (error) return <p className="error">{error}</p>;

  return (
    <div>
      <h2>Lista de Materias Primas</h2>

      {materias.length === 0 ? (
        <p>No hay registros. <Link to="/nuevo">Agregar el primero</Link></p>
      ) : (
        <table className="tabla">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Unidad</th>
              <th>Cantidad Actual</th>
              <th>Cantidad Mínima</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {materias.map(mp => (
              <tr key={mp.id}>
                <td>{mp.id}</td>
                <td>{mp.nombre}</td>
                <td>{mp.unidad}</td>
                <td>{mp.cantidadActual}</td>
                <td>{mp.cantidadMinima}</td>
                <td>
                  {mp.cantidadActual < mp.cantidadMinima
                    ? <span className="alerta">⚠ Bajo mínimo</span>
                    : <span className="ok">OK</span>
                  }
                </td>
                <td>
                  <Link to={`/editar/${mp.id}`} className="btn-editar">Editar</Link>
                  {' '}
                  <button onClick={() => handleEliminar(mp.id)} className="btn-eliminar">
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default MateriaPrimaLista;
