<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    pageContext.setAttribute("titulo", "Movimientos");
    pageContext.setAttribute("modulo", "movimientos");
%>
<jsp:include page="/jsp/includes/header.jsp" />
<jsp:include page="/jsp/includes/nav.jsp" />

<div class="contenedor">

    <%-- Mensaje de movimiento registrado --%>
    <c:if test="${param.mensaje eq 'registrado'}">
        <div class="alerta alerta-exito">Movimiento registrado correctamente.</div>
    </c:if>

    <div class="tarjeta">

        <div class="barra-herramientas">
            <div>
                <h2 class="tarjeta-titulo" style="margin-bottom:0; border:none;">
                    Movimientos de Inventario
                </h2>
                <c:if test="${not empty materialFiltro}">
                    <small style="color:#888;">
                        Filtrado por: ${materialFiltro.nombre}
                        &nbsp;|&nbsp;
                        <a href="${pageContext.request.contextPath}/movimientos">Ver todos</a>
                    </small>
                </c:if>
            </div>
            <a href="${pageContext.request.contextPath}/movimientos?accion=nuevo"
               class="btn btn-primario">
                + Registrar Movimiento
            </a>
        </div>

        <%-- Tabla de movimientos --%>
        <c:choose>
            <c:when test="${not empty movimientos}">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Fecha</th>
                            <th>Material</th>
                            <th>Tipo</th>
                            <th>Cantidad</th>
                            <th>Unidad</th>
                            <th>Usuario</th>
                            <th>Observación</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mov" items="${movimientos}">
                            <tr>
                                <td>${mov.id}</td>
                                <td>${mov.fecha}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/movimientos?materialId=${mov.materiaPrimaId}">
                                        ${mov.materiaPrimaNombre}
                                    </a>
                                </td>
                                <td>
                                    <span class="mov-${mov.tipo.toLowerCase()}">
                                        ${mov.tipo}
                                    </span>
                                </td>
                                <td><fmt:formatNumber value="${mov.cantidad}" pattern="#,##0.##"/></td>
                                <td>${mov.unidad}</td>
                                <td>${mov.usuarioRegistro}</td>
                                <td>${not empty mov.observacion ? mov.observacion : '-'}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <p style="margin-top:12px; color:#888; font-size:12px;">
                    Total: ${movimientos.size()} movimiento(s)
                </p>
            </c:when>
            <c:otherwise>
                <div class="alerta alerta-info">
                    No hay movimientos registrados.
                    <a href="${pageContext.request.contextPath}/movimientos?accion=nuevo">
                        Registrar el primero
                    </a>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<div class="footer">
    Sistema de Inventarios - GA7-220501096-AA2-EV02 &nbsp;|&nbsp; Diego Andrés Fonseca
</div>
</body>
</html>
