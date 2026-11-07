package com.inventario.modelo;

// Modelo de materia prima o insumo del inventario
// Autor: Diego Andrés Fonseca
public class MateriaPrima {

    private int id;
    private String nombre;
    private String unidad;
    private int cantidadActual;
    private int cantidadMinima;

    public MateriaPrima(int id, String nombre, String unidad, int cantidadActual, int cantidadMinima) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidadActual = cantidadActual;
        this.cantidadMinima = cantidadMinima;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public int getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(int cantidadActual) { this.cantidadActual = cantidadActual; }

    public int getCantidadMinima() { return cantidadMinima; }
    public void setCantidadMinima(int cantidadMinima) { this.cantidadMinima = cantidadMinima; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | " + cantidadActual + " " + unidad +
               " | Minimo: " + cantidadMinima;
    }
}
