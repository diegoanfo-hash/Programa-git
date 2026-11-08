package com.inventario.repositorio;

import com.inventario.modelo.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio Spring Data JPA: provee CRUD sin escribir SQL
// Autor: Diego Andrés Fonseca
@Repository
public interface MateriaPrimaRepositorio extends JpaRepository<MateriaPrima, Long> {

    // Buscar por nombre (Spring genera la consulta automaticamente)
    List<MateriaPrima> findByNombreContainingIgnoreCase(String nombre);
}
