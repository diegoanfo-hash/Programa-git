package com.inventario.datos;

import com.inventario.modelo.MateriaPrima;
import com.inventario.modelo.Movimiento;
import com.inventario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias para AlmacenDatos
// Autor: Diego Andrés Fonseca
@DisplayName("Pruebas de AlmacenDatos")
class AlmacenDatosTest {

    private AlmacenDatos almacen;

    @BeforeEach
    void setUp() {
        almacen = AlmacenDatos.getInstance();
    }

    // Autenticación

    @Test
    @DisplayName("Autenticar con credenciales válidas retorna el usuario")
    void autenticar_ConCredencialesValidas_RetornaUsuario() {
        Usuario resultado = almacen.autenticar("admin", "admin123");
        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsuario());
        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    @DisplayName("Autenticar con contraseña incorrecta retorna null")
    void autenticar_ConContrasenaIncorrecta_RetornaNull() {
        assertNull(almacen.autenticar("admin", "claveIncorrecta"));
    }

    @Test
    @DisplayName("Autenticar con usuario inexistente retorna null")
    void autenticar_ConUsuarioInexistente_RetornaNull() {
        assertNull(almacen.autenticar("usuarioQueNoExiste", "cualquierClave"));
    }

    @Test
    @DisplayName("Autenticar usuario dfonseca con credenciales correctas")
    void autenticar_UsuarioDfonseca_RetornaUsuario() {
        Usuario resultado = almacen.autenticar("dfonseca", "sena2026");
        assertNotNull(resultado);
        assertEquals("Diego Andrés Fonseca", resultado.getNombre());
        assertEquals("ADMIN", resultado.getRol());
    }

    // Materias primas

    @Test
    @DisplayName("Listar materias primas retorna lista no vacía")
    void listarMateriasPrimas_RetornaListaNoVacia() {
        List<MateriaPrima> materiales = almacen.listarMateriasPrimas();
        assertNotNull(materiales);
        assertFalse(materiales.isEmpty());
    }

    @Test
    @DisplayName("Buscar material por ID existente retorna el material correcto")
    void buscarPorId_ConIdExistente_RetornaMaterial() {
        MateriaPrima resultado = almacen.buscarMateriaPrimaPorId(1);
        assertNotNull(resultado);
        assertEquals("MP-001", resultado.getCodigo());
        assertEquals("Acero Galvanizado", resultado.getNombre());
    }

    @Test
    @DisplayName("Buscar material por ID inexistente retorna null")
    void buscarPorId_ConIdInexistente_RetornaNull() {
        assertNull(almacen.buscarMateriaPrimaPorId(99999));
    }

    @Test
    @DisplayName("Agregar material nuevo lo incluye en el listado")
    void agregarMaterial_SeIncluye_EnListado() {
        int cantidadInicial = almacen.listarMateriasPrimas().size();
        MateriaPrima nuevo = new MateriaPrima(0, "TEST-001", "Material de Prueba", "INSUMO", "unidad", 100.0, 20.0);

        almacen.agregarMateriaPrima(nuevo);

        assertEquals(cantidadInicial + 1, almacen.listarMateriasPrimas().size());
        assertTrue(nuevo.getId() > 0);
    }

    @Test
    @DisplayName("Buscar por nombre devuelve resultados coincidentes")
    void buscarPorNombre_ConNombreParcial_RetornaCoincidencias() {
        List<MateriaPrima> resultados = almacen.buscarPorNombre("Acero");
        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
    }

    @Test
    @DisplayName("Eliminar material lo quita del listado")
    void eliminarMaterial_YaNoApareceEnListado() {
        MateriaPrima temporal = new MateriaPrima(0, "DEL-001", "Material Temporal", "INSUMO", "unidad", 10.0, 5.0);
        almacen.agregarMateriaPrima(temporal);
        int idAsignado = temporal.getId();

        boolean eliminado = almacen.eliminarMateriaPrima(idAsignado);

        assertTrue(eliminado);
        assertNull(almacen.buscarMateriaPrimaPorId(idAsignado));
    }

    @Test
    @DisplayName("Filtrar por tipo MATERIA_PRIMA retorna solo ese tipo")
    void listarPorTipo_MateriaPrima_RetornaSoloeEseTipo() {
        List<MateriaPrima> materias = almacen.listarPorTipo("MATERIA_PRIMA");
        assertFalse(materias.isEmpty());
        materias.forEach(mp -> assertEquals("MATERIA_PRIMA", mp.getTipo()));
    }

    // Movimientos

    @Test
    @DisplayName("Registrar ENTRADA aumenta el stock del material")
    void registrarEntrada_AumentaStockDelMaterial() {
        MateriaPrima mp = almacen.buscarMateriaPrimaPorId(2);
        double stockAntes = mp.getStockActual();

        almacen.registrarMovimiento(new Movimiento(0, 2, mp.getNombre(), "ENTRADA", 50.0, mp.getUnidad(),
                LocalDate.now(), "admin", "Prueba entrada"));

        assertEquals(stockAntes + 50.0, almacen.buscarMateriaPrimaPorId(2).getStockActual(), 0.001);
    }

    @Test
    @DisplayName("Registrar SALIDA reduce el stock del material")
    void registrarSalida_ReduceStockDelMaterial() {
        MateriaPrima mp = almacen.buscarMateriaPrimaPorId(1);
        double stockAntes = mp.getStockActual();

        almacen.registrarMovimiento(new Movimiento(0, 1, mp.getNombre(), "SALIDA", 10.0, mp.getUnidad(),
                LocalDate.now(), "operario", "Prueba salida"));

        assertEquals(stockAntes - 10.0, almacen.buscarMateriaPrimaPorId(1).getStockActual(), 0.001);
    }

    @Test
    @DisplayName("El movimiento registrado aparece en el listado")
    void movimientoRegistrado_ApareceEnListado() {
        int cantidadAntes = almacen.listarMovimientos().size();
        MateriaPrima mp = almacen.buscarMateriaPrimaPorId(3);

        Movimiento nuevo = new Movimiento(0, 3, mp.getNombre(), "ENTRADA", 25.0, mp.getUnidad(),
                LocalDate.now(), "dfonseca", "Prueba listado");
        almacen.registrarMovimiento(nuevo);

        assertEquals(cantidadAntes + 1, almacen.listarMovimientos().size());
        assertTrue(nuevo.getId() > 0);
    }

    @Test
    @DisplayName("Listar movimientos por material retorna solo los de ese material")
    void listarMovimientosPorMaterial_RetornaSoloLosCorrespondientes() {
        List<Movimiento> movimientosMaterial1 = almacen.listarMovimientosPorMaterial(1);
        assertFalse(movimientosMaterial1.isEmpty());
        movimientosMaterial1.forEach(mov -> assertEquals(1, mov.getMateriaPrimaId()));
    }

    @Test
    @DisplayName("Listar alertas de stock solo incluye materiales CRITICO o BAJO")
    void listarAlertas_SoloIncluyeMaterialesCriticosYBajos() {
        List<MateriaPrima> alertas = almacen.listarConAlertaStock();
        alertas.forEach(mp -> assertNotEquals("NORMAL", mp.getEstado()));
    }
}
