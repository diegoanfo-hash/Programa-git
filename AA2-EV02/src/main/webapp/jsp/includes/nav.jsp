<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Barra de navegación principal del sistema --%>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/materias" class="navbar-brand">
        Inventarios
    </a>

    <ul class="navbar-nav">
        <li>
            <a href="${pageContext.request.contextPath}/materias"
               class="nav-link ${modulo eq 'materias' ? 'activo' : ''}">
                Materias Primas
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/movimientos"
               class="nav-link ${modulo eq 'movimientos' ? 'activo' : ''}">
                Movimientos
            </a>
        </li>
    </ul>

    <div style="display:flex; align-items:center; gap:12px;">
        <span class="nav-usuario">
            <c:choose>
                <c:when test="${not empty sessionScope.nombreUsuario}">
                    ${sessionScope.nombreUsuario}
                    <span style="color:#5a8bbf; font-size:11px;">(${sessionScope.rolUsuario})</span>
                </c:when>
                <c:otherwise>Invitado</c:otherwise>
            </c:choose>
        </span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-secundario btn-sm">
            Cerrar Sesión
        </a>
    </div>
</nav>
