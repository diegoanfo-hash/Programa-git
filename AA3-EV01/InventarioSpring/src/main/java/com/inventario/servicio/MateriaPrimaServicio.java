package com.inventario.servicio;

import com.inventario.modelo.MateriaPrima;
import com.inventario.repositorio.MateriaPrimaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio: contiene la logica de negocio del modulo de inventario
// Autor: Diego Andrés Fonseca
@Service
public class MateriaPrimaServicio {

    @Autowired
    private MateriaPrimaRepositorio repositorio;

    // Obtener todos los registros
    public List<MateriaPrima> obtenerTodos() {
        return repositorio.findAll();
    }

    // Obtener uno por ID
    public Optional<MateriaPrima> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    // Guardar (insertar o actualizar)
    public MateriaPrima guardar(MateriaPrima mp) {
        return repositorio.save(mp);
    }

    // Eliminar por ID
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    // Buscar por nombre
    public List<MateriaPrima> buscarPorNombre(String nombre) {
        return repositorio.findByNombreContainingIgnoreCase(nombre);
    }
}
