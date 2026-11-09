package com.inventario.api.controlador;

import com.inventario.api.modelo.MateriaPrima;
import com.inventario.api.repositorio.MateriaPrimaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriaPrimaControlador {

    @Autowired
    private MateriaPrimaRepositorio repositorio;

    @GetMapping
    public List<MateriaPrima> listarTodos() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrima> obtenerPorId(@PathVariable Long id) {
        Optional<MateriaPrima> mp = repositorio.findById(id);
        if (mp.isPresent()) {
            return ResponseEntity.ok(mp.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MateriaPrima> crear(@RequestBody MateriaPrima mp) {
        MateriaPrima guardado = repositorio.save(mp);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
