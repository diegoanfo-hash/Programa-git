package com.inventario.api.repositorio;

import com.inventario.api.modelo.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA: CRUD sin SQL manual
// Autor: Diego Andrés Fonseca
@Repository
public interface MateriaPrimaRepositorio extends JpaRepository<MateriaPrima, Long> {
}
