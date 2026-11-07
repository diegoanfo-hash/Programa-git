package com.inventario.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias para Movimiento
// Autor: Diego Andrés Fonseca
@DisplayName("Pruebas de Movimiento")
class MovimientoTest {

    @Test
    @DisplayName("El constructor asigna correctamente todos los campos")
    void constructor_AsignaTodosLosCampos() {
        LocalDate hoy = LocalDate.now();
        Movimiento mov = new Movimiento(201, 1, "Acero Galvanizado", "ENTRADA", 500.0, "kg", hoy, "admin", "Compra inicial");

        assertAll("Campos del constructor",
            () -> assertEquals(201,                 mov.getId()),
            () -> assertEquals(1,                   mov.getMateriaPrimaId()),
            () -> assertEquals("Acero Galvanizado", mov.getMateriaPrimaNombre()),
            () -> assertEquals("ENTRADA",           mov.getTipo()),
            () -> assertEquals(500.0,               mov.getCantidad()),
            () -> assertEquals("kg",                mov.getUnidad()),
            () -> assertEquals(hoy,                 mov.getFecha()),
            () -> assertEquals("admin",             mov.getUsuarioRegistro()),
            () -> assertEquals("Compra inicial",    mov.getObservacion())
        );
    }

    @Test
    @DisplayName("El tipo ENTRADA es válido")
    void tipoEntrada_EsValido() {
        Movimiento mov = new Movimiento(1, 1, "Acero", "ENTRADA", 100.0, "kg", LocalDate.now(), "admin", "");
        assertEquals("ENTRADA", mov.getTipo());
    }

    @Test
    @DisplayName("El tipo SALIDA es válido")
    void tipoSalida_EsValido() {
        Movimiento mov = new Movimiento(2, 2, "Aluminio", "SALIDA", 30.0, "kg", LocalDate.now(), "operario", "Producción");
        assertEquals("SALIDA", mov.getTipo());
    }

    @Test
    @DisplayName("La cantidad debe ser positiva")
    void cantidad_DebeSerPositiva() {
        Movimiento mov = new Movimiento(3, 1, "Acero", "ENTRADA", 250.5, "kg", LocalDate.now(), "admin", "");
        assertTrue(mov.getCantidad() > 0);
    }

    @Test
    @DisplayName("La fecha no puede ser nula")
    void fecha_NoEsNula() {
        Movimiento mov = new Movimiento(4, 1, "Acero", "ENTRADA", 100.0, "kg", LocalDate.now(), "admin", "");
        assertNotNull(mov.getFecha());
    }

    @Test
    @DisplayName("El toString contiene información relevante del movimiento")
    void toString_ContieneInformacionRelevante() {
        Movimiento mov = new Movimiento(10, 1, "Acero Galvanizado", "ENTRADA", 100.0, "kg", LocalDate.now(), "admin", "");
        String s = mov.toString();

        assertAll("toString debe contener datos clave",
            () -> assertTrue(s.contains("ENTRADA")),
            () -> assertTrue(s.contains("Acero Galvanizado")),
            () -> assertTrue(s.contains("100.0"))
        );
    }

    @Test
    @DisplayName("El setter de ID actualiza el valor correctamente")
    void setId_ActualizaCorrectamente() {
        Movimiento mov = new Movimiento();
        mov.setId(999);
        assertEquals(999, mov.getId());
    }
}
