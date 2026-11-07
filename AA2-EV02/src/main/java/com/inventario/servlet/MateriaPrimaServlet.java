package com.inventario.servlet;

import com.inventario.datos.AlmacenDatos;
import com.inventario.modelo.MateriaPrima;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Servlet CRUD del módulo de Materias Primas e Insumos
// Autor: Diego Andrés Fonseca
public class MateriaPrimaServlet extends HttpServlet {

    // GET: lista, busca o muestra formulario según el parámetro "accion"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null || accion.isEmpty()) accion = "listar";

        AlmacenDatos almacen = AlmacenDatos.getInstance();

        switch (accion) {
            case "listar":
                String tipo = request.getParameter("tipo");
                List<MateriaPrima> materiales = (tipo != null && !tipo.isEmpty())
                        ? almacen.listarPorTipo(tipo)
                        : almacen.listarMateriasPrimas();
                request.setAttribute("materiales", materiales);
                request.setAttribute("alertas", almacen.listarConAlertaStock());
                request.setAttribute("filtroTipo", tipo);
                request.getRequestDispatcher("/jsp/materias/lista.jsp").forward(request, response);
                break;

            case "buscar":
                String texto = request.getParameter("q");
                if (texto != null && !texto.trim().isEmpty()) {
                    request.setAttribute("materiales", almacen.buscarPorNombre(texto.trim()));
                    request.setAttribute("busqueda", texto.trim());
                } else {
                    request.setAttribute("materiales", almacen.listarMateriasPrimas());
                }
                request.setAttribute("alertas", almacen.listarConAlertaStock());
                request.getRequestDispatcher("/jsp/materias/lista.jsp").forward(request, response);
                break;

            case "nuevo":
                request.getRequestDispatcher("/jsp/materias/formulario.jsp").forward(request, response);
                break;

            case "editar":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    MateriaPrima mp = almacen.buscarMateriaPrimaPorId(id);
                    if (mp != null) {
                        request.setAttribute("material", mp);
                        request.getRequestDispatcher("/jsp/materias/formulario.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/materias");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/materias");
                }
                break;

            case "eliminar":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    almacen.eliminarMateriaPrima(id);
                    response.sendRedirect(request.getContextPath() + "/materias?mensaje=eliminado");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/materias");
                }
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/materias");
        }
    }

    // POST: crea o actualiza una materia prima
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        AlmacenDatos almacen = AlmacenDatos.getInstance();

        try {
            String codigo = request.getParameter("codigo").trim();
            String nombre = request.getParameter("nombre").trim();
            String tipo   = request.getParameter("tipo");
            String unidad = request.getParameter("unidad").trim();
            double stockActual = Double.parseDouble(request.getParameter("stockActual"));
            double stockMinimo = Double.parseDouble(request.getParameter("stockMinimo"));

            if ("crear".equals(accion)) {
                if (codigo.isEmpty() || nombre.isEmpty() || unidad.isEmpty()) {
                    request.setAttribute("error", "Todos los campos son obligatorios.");
                    request.getRequestDispatcher("/jsp/materias/formulario.jsp").forward(request, response);
                    return;
                }
                almacen.agregarMateriaPrima(new MateriaPrima(0, codigo, nombre, tipo, unidad, stockActual, stockMinimo));
                response.sendRedirect(request.getContextPath() + "/materias?mensaje=creado");

            } else if ("actualizar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                MateriaPrima actualizado = new MateriaPrima(id, codigo, nombre, tipo, unidad, stockActual, stockMinimo);
                boolean ok = almacen.actualizarMateriaPrima(actualizado);
                if (ok) {
                    response.sendRedirect(request.getContextPath() + "/materias?mensaje=actualizado");
                } else {
                    request.setAttribute("error", "No se pudo actualizar el material.");
                    request.setAttribute("material", actualizado);
                    request.getRequestDispatcher("/jsp/materias/formulario.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/materias");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Los valores de stock deben ser numéricos.");
            request.getRequestDispatcher("/jsp/materias/formulario.jsp").forward(request, response);
        }
    }
}
