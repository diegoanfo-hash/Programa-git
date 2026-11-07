package com.inventario.modelo;

// Modelo de materia prima o insumo del inventario
// Autor: Diego Andrés Fonseca
public class MateriaPrima {

    private int id;
    private String codigo;
    private String nombre;
    private String tipo;       // MATERIA_PRIMA, INSUMO
    private String unidad;     // kg, m, unidad, litro, etc.
    private double stockActual;
    private double stockMinimo;
    private String estado;     // NORMAL, BAJO, CRITICO

    public MateriaPrima() {}

    public MateriaPrima(int id, String codigo, String nombre, String tipo,
                        String unidad, double stockActual, double stockMinimo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.unidad = unidad;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.estado = calcularEstado(stockActual, stockMinimo);
    }

    // CRITICO: actual < minimo | BAJO: actual < minimo*2 | NORMAL: actual >= minimo*2
    private String calcularEstado(double actual, double minimo) {
        if (actual < minimo) return "CRITICO";
        if (actual < minimo * 2) return "BAJO";
        return "NORMAL";
    }

    public void recalcularEstado() {
        this.estado = calcularEstado(this.stockActual, this.stockMinimo);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public double getStockActual() { return stockActual; }
    public void setStockActual(double stockActual) {
        this.stockActual = stockActual;
        recalcularEstado();
    }

    public double getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = stockMinimo;
        recalcularEstado();
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "MateriaPrima{id=" + id + ", codigo='" + codigo + "', nombre='" + nombre +
               "', stock=" + stockActual + ", estado='" + estado + "'}";
    }
}
