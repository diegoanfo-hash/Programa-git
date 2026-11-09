package com.inventario.repositorio;

import com.inventario.modelo.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaPrimaRepositorio extends JpaRepository<MateriaPrima, Long> {

    List<MateriaPrima> findByNombreContainingIgnoreCase(String nombre);
}
