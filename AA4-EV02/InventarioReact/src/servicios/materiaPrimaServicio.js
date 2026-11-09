const BASE_URL = '/api/materias';

export async function obtenerTodas() {
  const respuesta = await fetch(BASE_URL);
  if (!respuesta.ok) throw new Error('Error al cargar datos');
  return respuesta.json();
}

export async function obtenerPorId(id) {
  const respuesta = await fetch(`${BASE_URL}/${id}`);
  if (!respuesta.ok) throw new Error('Registro no encontrado');
  return respuesta.json();
}

export async function crear(datos) {
  const respuesta = await fetch(BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(datos)
  });
  if (!respuesta.ok) throw new Error('Error al crear registro');
  return respuesta.json();
}

export async function actualizar(id, datos) {
  const respuesta = await fetch(`${BASE_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(datos)
  });
  if (!respuesta.ok) throw new Error('Error al actualizar registro');
  return respuesta.json();
}

export async function eliminar(id) {
  const respuesta = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE' });
  if (!respuesta.ok) throw new Error('Error al eliminar registro');
}
