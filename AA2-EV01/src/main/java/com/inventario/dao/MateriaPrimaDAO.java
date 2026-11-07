package com.inventario.dao;

import com.inventario.datos.ConexionDB;
import com.inventario.modelo.MateriaPrima;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Clase DAO: operaciones CRUD sobre materia_prima usando JDBC
// Autor: Diego Andrés Fonseca
public class MateriaPrimaDAO {

    // Insertar un nuevo registro
    public void insertar(MateriaPrima mp) {
        String sql = "INSERT INTO materia_prima (nombre, unidad, cantidad_actual, cantidad_minima) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, mp.getNombre());
            ps.setString(2, mp.getUnidad());
            ps.setInt(3, mp.getCantidadActual());
            ps.setInt(4, mp.getCantidadMinima());
            ps.executeUpdate();
            System.out.println("Insertado: " + mp.getNombre());
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    // Consultar todos los registros
    public List<MateriaPrima> consultarTodos() {
        List<MateriaPrima> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia_prima";

        try (Connection conexion = ConexionDB.obtenerConexion();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            while (resultado.next()) {
                MateriaPrima mp = new MateriaPrima(
                    resultado.getInt("id"),
                    resultado.getString("nombre"),
                    resultado.getString("unidad"),
                    resultado.getInt("cantidad_actual"),
                    resultado.getInt("cantidad_minima")
                );
                lista.add(mp);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
        return lista;
    }

    // Consultar por ID
    public MateriaPrima consultarPorId(int id) {
        String sql = "SELECT * FROM materia_prima WHERE id = ?";
        MateriaPrima mp = null;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                mp = new MateriaPrima(
                    resultado.getInt("id"),
                    resultado.getString("nombre"),
                    resultado.getString("unidad"),
                    resultado.getInt("cantidad_actual"),
                    resultado.getInt("cantidad_minima")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar por ID: " + e.getMessage());
        }
        return mp;
    }

    // Actualizar un registro
    public void actualizar(MateriaPrima mp) {
        String sql = "UPDATE materia_prima SET nombre=?, unidad=?, cantidad_actual=?, " +
                     "cantidad_minima=? WHERE id=?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, mp.getNombre());
            ps.setString(2, mp.getUnidad());
            ps.setInt(3, mp.getCantidadActual());
            ps.setInt(4, mp.getCantidadMinima());
            ps.setInt(5, mp.getId());
            ps.executeUpdate();
            System.out.println("Actualizado: " + mp.getNombre());
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    // Eliminar un registro por ID
    public void eliminar(int id) {
        String sql = "DELETE FROM materia_prima WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Eliminado. ID: " + id);
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}
