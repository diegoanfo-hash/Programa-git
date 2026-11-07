package com.inventario.servlet;

import com.inventario.datos.AlmacenDatos;
import com.inventario.modelo.MateriaPrima;
import com.inventario.modelo.Movimiento;
import com.inventario.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

// Servlet del módulo de Movimientos de Inventario (entradas y salidas)
// Autor: Diego Andrés Fonseca
public class MovimientoServlet extends HttpServlet {

    // GET: lista movimientos o muestra el formulario de registro
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null || accion.isEmpty()) accion = "listar";

        AlmacenDatos almacen = AlmacenDatos.getInstance();

        switch (accion) {
            case "listar":
                String idMatParam = request.getParameter("materialId");
                List<Movimiento> movimientos;
                if (idMatParam != null && !idMatParam.isEmpty()) {
                    try {
                        int materialId = Integer.parseInt(idMatParam);
                        movimientos = almacen.listarMovimientosPorMaterial(materialId);
                        request.setAttribute("materialFiltro", almacen.buscarMateriaPrimaPorId(materialId));
                    } catch (NumberFormatException e) {
                        movimientos = almacen.listarMovimientos();
                    }
                } else {
                    movimientos = almacen.listarMovimientos();
                }
                request.setAttribute("movimientos", movimientos);
                request.getRequestDispatcher("/jsp/movimientos/lista.jsp").forward(request, response);
                break;

            case "nuevo":
                List<MateriaPrima> materiales = almacen.listarMateriasPrimas();
                request.setAttribute("materiales", materiales);
                // Pre-seleccionar material si viene por parámetro
                String preSelId = request.getParameter("materialId");
                if (preSelId != null && !preSelId.isEmpty()) {
                    request.setAttribute("materialPreseleccionado", preSelId);
                }
                request.getRequestDispatcher("/jsp/movimientos/formulario.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/movimientos");
        }
    }

    // POST: registra un nuevo movimiento de entrada o salida
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        AlmacenDatos almacen = AlmacenDatos.getInstance();
        HttpSession sesion = request.getSession(false);

        try {
            int materiaPrimaId = Integer.parseInt(request.getParameter("materiaPrimaId"));
            String tipo        = request.getParameter("tipo");
            double cantidad    = Double.parseDouble(request.getParameter("cantidad"));
            String observacion = request.getParameter("observacion");

            MateriaPrima mp = almacen.buscarMateriaPrimaPorId(materiaPrimaId);
            if (mp == null) {
                request.setAttribute("error", "Material no encontrado.");
                request.setAttribute("materiales", almacen.listarMateriasPrimas());
                request.getRequestDispatcher("/jsp/movimientos/formulario.jsp").forward(request, response);
                return;
            }

            // Validar stock suficiente para salidas
            if ("SALIDA".equals(tipo) && cantidad > mp.getStockActual()) {
                request.setAttribute("error", "Stock insuficiente. Stock actual: " +
                        mp.getStockActual() + " " + mp.getUnidad());
                request.setAttribute("materiales", almacen.listarMateriasPrimas());
                request.getRequestDispatcher("/jsp/movimientos/formulario.jsp").forward(request, response);
                return;
            }

            String usuarioRegistro = "sistema";
            if (sesion != null && sesion.getAttribute("usuario") != null) {
                usuarioRegistro = ((Usuario) sesion.getAttribute("usuario")).getUsuario();
            }

            Movimiento mov = new Movimiento(
                0, materiaPrimaId, mp.getNombre(),
                tipo, cantidad, mp.getUnidad(),
                LocalDate.now(), usuarioRegistro,
                observacion != null ? observacion.trim() : ""
            );

            almacen.registrarMovimiento(mov);
            response.sendRedirect(request.getContextPath() + "/movimientos?mensaje=registrado");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Los valores ingresados no son válidos.");
            request.setAttribute("materiales", almacen.listarMateriasPrimas());
            request.getRequestDispatcher("/jsp/movimientos/formulario.jsp").forward(request, response);
        }
    }
}
