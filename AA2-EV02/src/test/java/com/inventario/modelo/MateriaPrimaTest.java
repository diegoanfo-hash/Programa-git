package com.inventario.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias para MateriaPrima
// Autor: Diego Andrés Fonseca
@DisplayName("Pruebas de MateriaPrima")
class MateriaPrimaTest {

    @Test
    @DisplayName("Estado CRITICO cuando stock actual es menor al mínimo")
    void estadoCritico_CuandoStockActualMenorAlMinimo() {
        MateriaPrima mp = new MateriaPrima(1, "MP-001", "Acero", "MATERIA_PRIMA", "kg", 50.0, 100.0);
        assertEquals("CRITICO", mp.getEstado());
    }

    @Test
    @DisplayName("Estado BAJO cuando stock está entre mínimo y doble del mínimo")
    void estadoBajo_CuandoStockEntreMinimoYDoble() {
        MateriaPrima mp = new MateriaPrima(2, "MP-002", "Aluminio", "MATERIA_PRIMA", "kg", 150.0, 100.0);
        assertEquals("BAJO", mp.getEstado());
    }

    @Test
    @DisplayName("Estado NORMAL cuando stock es mayor o igual al doble del mínimo")
    void estadoNormal_CuandoStockMayorOIgualAlDoble() {
        MateriaPrima mp = new MateriaPrima(3, "MP-003", "Vidrio", "MATERIA_PRIMA", "m2", 200.0, 100.0);
        assertEquals("NORMAL", mp.getEstado());
    }

    @Test
    @DisplayName("Estado NORMAL cuando stock supera ampliamente el mínimo")
    void estadoNormal_CuandoStockMuyAlto() {
        MateriaPrima mp = new MateriaPrima(4, "IN-001", "Tornillos", "INSUMO", "unidad", 5000.0, 1000.0);
        assertEquals("NORMAL", mp.getEstado());
    }

    @Test
    @DisplayName("Estado CRITICO cuando stock es exactamente cero")
    void estadoCritico_CuandoStockEsCero() {
        MateriaPrima mp = new MateriaPrima(5, "IN-002", "Silicona", "INSUMO", "unidad", 0.0, 50.0);
        assertEquals("CRITICO", mp.getEstado());
    }

    @Test
    @DisplayName("El estado se recalcula automáticamente al actualizar el stock")
    void estadoSeRecalcula_CuandoStockCambia() {
        MateriaPrima mp = new MateriaPrima(6, "MP-004", "Perfil", "MATERIA_PRIMA", "m", 50.0, 100.0);
        assertEquals("CRITICO", mp.getEstado());

        mp.setStockActual(300.0);
        assertEquals("NORMAL", mp.getEstado());
    }

    @Test
    @DisplayName("El estado cambia de NORMAL a CRITICO al reducir el stock")
    void estadoCambiaACritico_CuandoStockBaja() {
        MateriaPrima mp = new MateriaPrima(7, "MP-005", "Bisagras", "INSUMO", "unidad", 500.0, 100.0);
        assertEquals("NORMAL", mp.getEstado());

        mp.setStockActual(30.0);
        assertEquals("CRITICO", mp.getEstado());
    }

    @Test
    @DisplayName("El constructor asigna correctamente todos los campos")
    void constructor_AsignaTodosLosCampos() {
        MateriaPrima mp = new MateriaPrima(10, "MP-010", "Pintura", "INSUMO", "litro", 120.0, 60.0);

        assertAll("Campos del constructor",
            () -> assertEquals(10,        mp.getId()),
            () -> assertEquals("MP-010",  mp.getCodigo()),
            () -> assertEquals("Pintura", mp.getNombre()),
            () -> assertEquals("INSUMO",  mp.getTipo()),
            () -> assertEquals("litro",   mp.getUnidad()),
            () -> assertEquals(120.0,     mp.getStockActual()),
            () -> assertEquals(60.0,      mp.getStockMinimo())
        );
    }

    @Test
    @DisplayName("El stock puede llegar a cero sin volverse negativo")
    void stockNoDebeSerNegativo_DespuesDeSalida() {
        MateriaPrima mp = new MateriaPrima(8, "IN-003", "Sellante", "INSUMO", "unidad", 10.0, 5.0);
        mp.setStockActual(0.0);

        assertTrue(mp.getStockActual() >= 0);
        assertEquals("CRITICO", mp.getEstado());
    }
}
