package com.inventario.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Maneja la conexion JDBC a la base de datos H2
// Autor: Diego Andrés Fonseca
public class ConexionDB {

    private static final String URL = "jdbc:h2:./inventario;DB_CLOSE_DELAY=-1";
    private static final String USUARIO = "sa";
    private static final String CLAVE = "";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    public static void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS materia_prima (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY," +
                     "nombre VARCHAR(100) NOT NULL," +
                     "unidad VARCHAR(20) NOT NULL," +
                     "cantidad_actual INT NOT NULL," +
                     "cantidad_minima INT NOT NULL)";

        try (Connection conexion = obtenerConexion();
             Statement sentencia = conexion.createStatement()) {
            sentencia.execute(sql);
            System.out.println("Tabla lista.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}
