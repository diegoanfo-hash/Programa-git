package com.inventario.servicio;

import com.inventario.modelo.MateriaPrima;
import com.inventario.repositorio.MateriaPrimaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaPrimaServicio {

    @Autowired
    private MateriaPrimaRepositorio repositorio;

    public List<MateriaPrima> obtenerTodos() {
        return repositorio.findAll();
    }

    public Optional<MateriaPrima> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public MateriaPrima guardar(MateriaPrima mp) {
        return repositorio.save(mp);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    public List<MateriaPrima> buscarPorNombre(String nombre) {
        return repositorio.findByNombreContainingIgnoreCase(nombre);
    }
}
