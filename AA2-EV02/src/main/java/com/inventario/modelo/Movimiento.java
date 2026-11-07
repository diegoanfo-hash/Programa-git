package com.inventario.modelo;

import java.time.LocalDate;

// Modelo de movimiento de inventario (entrada o salida de material)
// Autor: Diego Andrés Fonseca
public class Movimiento {

    private int id;
    private int materiaPrimaId;
    private String materiaPrimaNombre;
    private String tipo;         // ENTRADA, SALIDA
    private double cantidad;
    private String unidad;
    private LocalDate fecha;
    private String usuarioRegistro;
    private String observacion;

    public Movimiento() {}

    public Movimiento(int id, int materiaPrimaId, String materiaPrimaNombre,
                      String tipo, double cantidad, String unidad,
                      LocalDate fecha, String usuarioRegistro, String observacion) {
        this.id = id;
        this.materiaPrimaId = materiaPrimaId;
        this.materiaPrimaNombre = materiaPrimaNombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.fecha = fecha;
        this.usuarioRegistro = usuarioRegistro;
        this.observacion = observacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMateriaPrimaId() { return materiaPrimaId; }
    public void setMateriaPrimaId(int materiaPrimaId) { this.materiaPrimaId = materiaPrimaId; }

    public String getMateriaPrimaNombre() { return materiaPrimaNombre; }
    public void setMateriaPrimaNombre(String materiaPrimaNombre) { this.materiaPrimaNombre = materiaPrimaNombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getUsuarioRegistro() { return usuarioRegistro; }
    public void setUsuarioRegistro(String usuarioRegistro) { this.usuarioRegistro = usuarioRegistro; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    @Override
    public String toString() {
        return "Movimiento{id=" + id + ", tipo='" + tipo + "', material='" + materiaPrimaNombre +
               "', cantidad=" + cantidad + ", fecha=" + fecha + "}";
    }
}
