package com.inventario.datos;

import com.inventario.modelo.MateriaPrima;
import com.inventario.modelo.Movimiento;
import com.inventario.modelo.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Almacén de datos en memoria (patrón Singleton)
// Simula la base de datos del sistema de inventarios
// Autor: Diego Andrés Fonseca
public class AlmacenDatos {

    private static AlmacenDatos instancia;

    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<MateriaPrima> materiasPrimas = new ArrayList<>();
    private final List<Movimiento> movimientos = new ArrayList<>();

    private final AtomicInteger contadorMateriales = new AtomicInteger(100);
    private final AtomicInteger contadorMovimientos = new AtomicInteger(200);

    private AlmacenDatos() {
        inicializarDatos();
    }

    public static synchronized AlmacenDatos getInstance() {
        if (instancia == null) {
            instancia = new AlmacenDatos();
        }
        return instancia;
    }

    private void inicializarDatos() {
        // Usuarios
        usuarios.add(new Usuario(1, "Administrador", "admin", "admin123", "ADMIN"));
        usuarios.add(new Usuario(2, "Operario Bodega", "operario", "op123", "OPERARIO"));
        usuarios.add(new Usuario(3, "Diego Andrés Fonseca", "dfonseca", "sena2026", "ADMIN"));

        // Materias primas e insumos
        materiasPrimas.add(new MateriaPrima(1, "MP-001", "Acero Galvanizado",    "MATERIA_PRIMA", "kg",     850.0, 200.0));
        materiasPrimas.add(new MateriaPrima(2, "MP-002", "Aluminio en Lámina",   "MATERIA_PRIMA", "kg",     320.0, 150.0));
        materiasPrimas.add(new MateriaPrima(3, "MP-003", "Vidrio Templado",      "MATERIA_PRIMA", "m2",     180.0, 100.0));
        materiasPrimas.add(new MateriaPrima(4, "MP-004", "Perfil de Aluminio",   "MATERIA_PRIMA", "m",       90.0, 100.0));
        materiasPrimas.add(new MateriaPrima(5, "IN-001", "Tornillos Autoperforantes", "INSUMO",   "unidad", 5000.0, 1000.0));
        materiasPrimas.add(new MateriaPrima(6, "IN-002", "Silicona Sellante",    "INSUMO",        "unidad",   45.0,   50.0));
        materiasPrimas.add(new MateriaPrima(7, "IN-003", "Pintura Anticorrosiva","INSUMO",        "litro",   120.0,   60.0));
        materiasPrimas.add(new MateriaPrima(8, "MP-005", "Bisagras de Acero",    "INSUMO",        "unidad",   30.0,  100.0));

        // Movimientos iniciales
        movimientos.add(new Movimiento(201, 1, "Acero Galvanizado",  "ENTRADA", 500.0, "kg",     LocalDate.now().minusDays(10), "admin",    "Compra inicial"));
        movimientos.add(new Movimiento(202, 1, "Acero Galvanizado",  "SALIDA",  150.0, "kg",     LocalDate.now().minusDays(5),  "operario", "Producción lote #12"));
        movimientos.add(new Movimiento(203, 2, "Aluminio en Lámina", "ENTRADA", 200.0, "kg",     LocalDate.now().minusDays(8),  "admin",    "Compra proveedor AluminCol"));
        movimientos.add(new Movimiento(204, 6, "Silicona Sellante",  "SALIDA",   20.0, "unidad", LocalDate.now().minusDays(3),  "operario", "Producción puertas"));
        movimientos.add(new Movimiento(205, 8, "Bisagras de Acero",  "SALIDA",   80.0, "unidad", LocalDate.now().minusDays(2),  "operario", "Pedido cliente #45"));
    }

    // Usuarios

    public Usuario autenticar(String usuario, String contrasena) {
        return usuarios.stream()
                .filter(u -> u.getUsuario().equals(usuario) && u.getContrasena().equals(contrasena))
                .findFirst().orElse(null);
    }

    // Materias primas

    public List<MateriaPrima> listarMateriasPrimas() {
        return new ArrayList<>(materiasPrimas);
    }

    public List<MateriaPrima> listarPorTipo(String tipo) {
        List<MateriaPrima> resultado = new ArrayList<>();
        for (MateriaPrima mp : materiasPrimas) {
            if (mp.getTipo().equalsIgnoreCase(tipo)) resultado.add(mp);
        }
        return resultado;
    }

    public MateriaPrima buscarMateriaPrimaPorId(int id) {
        return materiasPrimas.stream().filter(mp -> mp.getId() == id).findFirst().orElse(null);
    }

    public List<MateriaPrima> buscarPorNombre(String texto) {
        List<MateriaPrima> resultado = new ArrayList<>();
        for (MateriaPrima mp : materiasPrimas) {
            if (mp.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                mp.getCodigo().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(mp);
            }
        }
        return resultado;
    }

    public synchronized void agregarMateriaPrima(MateriaPrima mp) {
        mp.setId(contadorMateriales.incrementAndGet());
        materiasPrimas.add(mp);
    }

    public synchronized boolean actualizarMateriaPrima(MateriaPrima actualizada) {
        for (int i = 0; i < materiasPrimas.size(); i++) {
            if (materiasPrimas.get(i).getId() == actualizada.getId()) {
                materiasPrimas.set(i, actualizada);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean eliminarMateriaPrima(int id) {
        return materiasPrimas.removeIf(mp -> mp.getId() == id);
    }

    public List<MateriaPrima> listarConAlertaStock() {
        List<MateriaPrima> alertas = new ArrayList<>();
        for (MateriaPrima mp : materiasPrimas) {
            if (!"NORMAL".equals(mp.getEstado())) alertas.add(mp);
        }
        return alertas;
    }

    // Movimientos

    public List<Movimiento> listarMovimientos() {
        return new ArrayList<>(movimientos);
    }

    public synchronized void registrarMovimiento(Movimiento mov) {
        mov.setId(contadorMovimientos.incrementAndGet());
        movimientos.add(mov);

        // Actualizar stock del material afectado
        MateriaPrima mp = buscarMateriaPrimaPorId(mov.getMateriaPrimaId());
        if (mp != null) {
            if ("ENTRADA".equals(mov.getTipo())) {
                mp.setStockActual(mp.getStockActual() + mov.getCantidad());
            } else if ("SALIDA".equals(mov.getTipo())) {
                mp.setStockActual(mp.getStockActual() - mov.getCantidad());
            }
        }
    }

    public List<Movimiento> listarMovimientosPorMaterial(int materiaPrimaId) {
        List<Movimiento> resultado = new ArrayList<>();
        for (Movimiento m : movimientos) {
            if (m.getMateriaPrimaId() == materiaPrimaId) resultado.add(m);
        }
        return resultado;
    }
}
