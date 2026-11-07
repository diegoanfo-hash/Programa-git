<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Página de entrada: redirige al login o al módulo principal según el estado de la sesión --%>
<%
    if (session.getAttribute("usuario") != null) {
        response.sendRedirect(request.getContextPath() + "/materias");
    } else {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>
