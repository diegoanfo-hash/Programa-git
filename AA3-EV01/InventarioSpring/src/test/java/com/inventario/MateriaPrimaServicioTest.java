package com.inventario;

import com.inventario.modelo.MateriaPrima;
import com.inventario.repositorio.MateriaPrimaRepositorio;
import com.inventario.servicio.MateriaPrimaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias del servicio de inventario
// GA7-220501096-AA3-EV01
// Autor: Diego Andrés Fonseca
@SpringBootTest
class MateriaPrimaServicioTest {

    @Autowired
    private MateriaPrimaServicio servicio;

    @Autowired
    private MateriaPrimaRepositorio repositorio;

    @BeforeEach
    void limpiar() {
        repositorio.deleteAll();
    }

    @Test
    void guardarYConsultar() {
        MateriaPrima mp = new MateriaPrima("Acero", "kg", 100, 30);
        servicio.guardar(mp);

        List<MateriaPrima> lista = servicio.obtenerTodos();
        assertEquals(1, lista.size());
        assertEquals("Acero", lista.get(0).getNombre());
    }

    @Test
    void consultarPorId() {
        MateriaPrima mp = servicio.guardar(new MateriaPrima("Vidrio", "m2", 50, 20));

        Optional<MateriaPrima> encontrado = servicio.obtenerPorId(mp.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Vidrio", encontrado.get().getNombre());
    }

    @Test
    void actualizar() {
        MateriaPrima mp = servicio.guardar(new MateriaPrima("Aluminio", "ml", 80, 25));
        mp.setCantidadActual(60);
        servicio.guardar(mp);

        Optional<MateriaPrima> actualizado = servicio.obtenerPorId(mp.getId());
        assertEquals(60, actualizado.get().getCantidadActual());
    }

    @Test
    void eliminar() {
        MateriaPrima mp = servicio.guardar(new MateriaPrima("Cobre", "kg", 30, 10));
        servicio.eliminar(mp.getId());

        Optional<MateriaPrima> eliminado = servicio.obtenerPorId(mp.getId());
        assertFalse(eliminado.isPresent());
    }

    @Test
    void detectarBajoMinimo() {
        MateriaPrima mp = new MateriaPrima("Plastico", "kg", 10, 50);
        assertTrue(mp.estaBajoMinimo());

        mp.setCantidadActual(60);
        assertFalse(mp.estaBajoMinimo());
    }

    @Test
    void buscarPorNombre() {
        servicio.guardar(new MateriaPrima("Acero galvanizado", "kg", 100, 30));
        servicio.guardar(new MateriaPrima("Acero inoxidable", "kg", 50, 20));
        servicio.guardar(new MateriaPrima("Vidrio templado", "m2", 30, 10));

        List<MateriaPrima> resultado = servicio.buscarPorNombre("acero");
        assertEquals(2, resultado.size());
    }
}
