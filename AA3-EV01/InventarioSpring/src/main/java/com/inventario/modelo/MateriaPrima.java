package com.inventario.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad JPA que representa una materia prima del inventario
// Autor: Diego Andrés Fonseca
@Entity
@Table(name = "materia_prima")
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del material
    private String nombre;

    // Unidad de medida (kg, m2, ml, etc.)
    private String unidad;

    // Cantidad disponible actualmente
    private int cantidadActual;

    // Cantidad minima requerida
    private int cantidadMinima;

    public MateriaPrima() {}

    public MateriaPrima(String nombre, String unidad, int cantidadActual, int cantidadMinima) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidadActual = cantidadActual;
        this.cantidadMinima = cantidadMinima;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public int getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(int cantidadActual) { this.cantidadActual = cantidadActual; }

    public int getCantidadMinima() { return cantidadMinima; }
    public void setCantidadMinima(int cantidadMinima) { this.cantidadMinima = cantidadMinima; }

    // Indica si el stock esta por debajo del minimo
    public boolean estaBajoMinimo() {
        return cantidadActual < cantidadMinima;
    }
}
