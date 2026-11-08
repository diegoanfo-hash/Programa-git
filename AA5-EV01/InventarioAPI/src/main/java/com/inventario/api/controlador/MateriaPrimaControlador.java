package com.inventario.api.controlador;

import com.inventario.api.modelo.MateriaPrima;
import com.inventario.api.repositorio.MateriaPrimaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controlador REST: expone los endpoints de la API de inventario
// Permite peticiones desde cualquier origen (CORS abierto para desarrollo)
// GA7-220501096-AA5-EV01
// Autor: Diego Andrés Fonseca
@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriaPrimaControlador {

    @Autowired
    private MateriaPrimaRepositorio repositorio;

    // GET /api/materias
    // Servicio: lista todas las materias primas
    // Retorna: 200 OK con array JSON
    @GetMapping
    public List<MateriaPrima> listarTodos() {
        return repositorio.findAll();
    }

    // GET /api/materias/{id}
    // Servicio: obtiene una materia prima por su ID
    // Retorna: 200 OK con objeto JSON, o 404 si no existe
    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrima> obtenerPorId(@PathVariable Long id) {
        Optional<MateriaPrima> mp = repositorio.findById(id);
        if (mp.isPresent()) {
            return ResponseEntity.ok(mp.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/materias
    // Servicio: crea una nueva materia prima
    // Body: JSON con nombre, unidad, cantidadActual, cantidadMinima
    // Retorna: 201 Created con el objeto creado (incluye el ID generado)
    @PostMapping
    public ResponseEntity<MateriaPrima> crear(@RequestBody MateriaPrima mp) {
        MateriaPrima guardado = repositorio.save(mp);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/materias/{id}
    // Servicio: actualiza una materia prima existente
    // Body: JSON con los campos a actualizar
    // Retorna: 200 OK con el objeto actualizado, o 404 si no existe
    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrima> actualizar(@PathVariable Long id, @RequestBody MateriaPrima datos) {
        Optional<MateriaPrima> existente = repositorio.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MateriaPrima mp = existente.get();
        mp.setNombre(datos.getNombre());
        mp.setUnidad(datos.getUnidad());
        mp.setCantidadActual(datos.getCantidadActual());
        mp.setCantidadMinima(datos.getCantidadMinima());
        return ResponseEntity.ok(repositorio.save(mp));
    }

    // DELETE /api/materias/{id}
    // Servicio: elimina una materia prima por ID
    // Retorna: 204 No Content si se eliminó, o 404 si no existe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
