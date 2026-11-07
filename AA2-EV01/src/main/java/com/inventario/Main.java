package com.inventario;

import com.inventario.datos.ConexionDB;
import com.inventario.dao.MateriaPrimaDAO;
import com.inventario.modelo.MateriaPrima;

import java.util.List;

// Clase principal: demuestra las operaciones CRUD del modulo de inventario
// GA7-220501096-AA2-EV01 - Codificacion de modulos del software
// Autor: Diego Andrés Fonseca
public class Main {

    public static void main(String[] args) {

        System.out.println("GA7-220501096-AA2-EV01 - Modulo de Inventario con JDBC");
        System.out.println("Autor: Diego Andres Fonseca");
        System.out.println("--------------------------------------------------------");

        // Crear la tabla si no existe
        ConexionDB.crearTabla();

        MateriaPrimaDAO dao = new MateriaPrimaDAO();

        // 1. INSERTAR
        System.out.println("\n[INSERTAR]");
        dao.insertar(new MateriaPrima(0, "Lamina de acero", "kg", 150, 50));
        dao.insertar(new MateriaPrima(0, "Vidrio templado", "m2", 80, 30));
        dao.insertar(new MateriaPrima(0, "Perfil de aluminio", "ml", 200, 60));

        // 2. CONSULTAR TODOS
        System.out.println("\n[CONSULTAR TODOS]");
        List<MateriaPrima> lista = dao.consultarTodos();
        for (MateriaPrima mp : lista) {
            System.out.println(mp);
        }

        // 3. ACTUALIZAR
        System.out.println("\n[ACTUALIZAR]");
        MateriaPrima mpModificada = new MateriaPrima(1, "Lamina de acero galvanizado", "kg", 120, 50);
        dao.actualizar(mpModificada);
        System.out.println("Registro actualizado: " + dao.consultarPorId(1));

        // 4. ELIMINAR
        System.out.println("\n[ELIMINAR]");
        dao.eliminar(3);

        // Resultado final
        System.out.println("\n[REGISTROS FINALES]");
        lista = dao.consultarTodos();
        for (MateriaPrima mp : lista) {
            System.out.println(mp);
        }

        System.out.println("\nOperaciones CRUD completadas correctamente.");
    }
}
