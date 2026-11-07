package com.inventario.modelo;

// Modelo de usuario del sistema
// Autor: Diego Andrés Fonseca
public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String rol; // ADMIN, OPERARIO

    public Usuario() {}

    public Usuario(int id, String nombre, String usuario, String contrasena, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', usuario='" + usuario + "', rol='" + rol + "'}";
    }
}
