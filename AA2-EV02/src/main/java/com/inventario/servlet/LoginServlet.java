package com.inventario.servlet;

import com.inventario.datos.AlmacenDatos;
import com.inventario.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Servlet de inicio de sesión
// Autor: Diego Andrés Fonseca
public class LoginServlet extends HttpServlet {

    // GET: muestra el formulario de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion != null && sesion.getAttribute("usuario") != null) {
            response.sendRedirect(request.getContextPath() + "/materias");
            return;
        }

        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    // POST: procesa las credenciales del formulario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        if (usuario == null || usuario.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingrese usuario y contraseña.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        AlmacenDatos almacen = AlmacenDatos.getInstance();
        Usuario usuarioAutenticado = almacen.autenticar(usuario.trim(), contrasena.trim());

        if (usuarioAutenticado != null) {
            HttpSession sesion = request.getSession(true);
            sesion.setAttribute("usuario", usuarioAutenticado);
            sesion.setAttribute("nombreUsuario", usuarioAutenticado.getNombre());
            sesion.setAttribute("rolUsuario", usuarioAutenticado.getRol());
            sesion.setMaxInactiveInterval(30 * 60); // 30 minutos

            response.sendRedirect(request.getContextPath() + "/materias");
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos. Verifique e intente de nuevo.");
            request.setAttribute("usuarioIngresado", usuario);
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}
