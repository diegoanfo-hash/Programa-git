<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    pageContext.setAttribute("titulo", "Materias Primas");
    pageContext.setAttribute("modulo", "materias");
%>
<jsp:include page="/jsp/includes/header.jsp" />
<jsp:include page="/jsp/includes/nav.jsp" />

<div class="contenedor">

    <%-- Mensajes de operación exitosa --%>
    <c:if test="${param.mensaje eq 'creado'}">
        <div class="alerta alerta-exito">Material registrado correctamente.</div>
    </c:if>
    <c:if test="${param.mensaje eq 'actualizado'}">
        <div class="alerta alerta-exito">Material actualizado correctamente.</div>
    </c:if>
    <c:if test="${param.mensaje eq 'eliminado'}">
        <div class="alerta alerta-exito">Material eliminado del sistema.</div>
    </c:if>

    <%-- Alerta de stock crítico o bajo --%>
    <c:if test="${not empty alertas}">
        <div class="alerta alerta-advertencia">
            <strong>Alerta de stock:</strong> ${alertas.size()} material(es) requieren atención inmediata.
        </div>
    </c:if>

    <div class="tarjeta">

        <div class="barra-herramientas">
            <h2 class="tarjeta-titulo" style="margin-bottom:0; border:none;">
                Materias Primas e Insumos
            </h2>
            <a href="${pageContext.request.contextPath}/materias?accion=nuevo"
               class="btn btn-primario">
                + Nuevo Material
            </a>
        </div>

        <%-- Barra de búsqueda y filtros - Método GET --%>
        <form action="${pageContext.request.contextPath}/materias" method="get"
              style="display:flex; gap:10px; margin-bottom:18px; flex-wrap:wrap;">
            <input type="hidden" name="accion" value="buscar">
            <input type="text" name="q" placeholder="Buscar por nombre o código..."
                   value="${busqueda}" style="width:260px;">
            <button type="submit" class="btn btn-secundario">Buscar</button>
            <a href="${pageContext.request.contextPath}/materias" class="btn btn-secundario">
                Ver todos
            </a>

            <%-- Filtro por tipo --%>
            <form action="${pageContext.request.contextPath}/materias" method="get"
                  style="display:flex; gap:8px;">
                <select name="tipo" onchange="this.form.submit()">
                    <option value="">Todos los tipos</option>
                    <option value="MATERIA_PRIMA" ${filtroTipo eq 'MATERIA_PRIMA' ? 'selected' : ''}>
                        Materia Prima
                    </option>
                    <option value="INSUMO" ${filtroTipo eq 'INSUMO' ? 'selected' : ''}>
                        Insumo
                    </option>
                </select>
            </form>
        </form>

        <%-- Tabla de materiales --%>
        <c:choose>
            <c:when test="${not empty materiales}">
                <table>
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Tipo</th>
                            <th>Unidad</th>
                            <th>Stock Actual</th>
                            <th>Stock Mínimo</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mp" items="${materiales}">
                            <tr>
                                <td>${mp.codigo}</td>
                                <td>${mp.nombre}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mp.tipo eq 'MATERIA_PRIMA'}">Materia Prima</c:when>
                                        <c:otherwise>Insumo</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${mp.unidad}</td>
                                <td><fmt:formatNumber value="${mp.stockActual}" pattern="#,##0.##"/></td>
                                <td><fmt:formatNumber value="${mp.stockMinimo}" pattern="#,##0.##"/></td>
                                <td>
                                    <span class="estado-${mp.estado.toLowerCase()}">
                                        ${mp.estado}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/materias?accion=editar&id=${mp.id}"
                                       class="btn btn-secundario btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/movimientos?accion=nuevo&materialId=${mp.id}"
                                       class="btn btn-exito btn-sm">Movimiento</a>
                                    <a href="${pageContext.request.contextPath}/materias?accion=eliminar&id=${mp.id}"
                                       class="btn btn-peligro btn-sm"
                                       onclick="return confirm('¿Está seguro de eliminar este material?')">
                                        Eliminar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <p style="margin-top:12px; color:#888; font-size:12px;">
                    Total: ${materiales.size()} registro(s)
                </p>
            </c:when>
            <c:otherwise>
                <div class="alerta alerta-info">
                    No se encontraron materiales.
                    <a href="${pageContext.request.contextPath}/materias?accion=nuevo">Registrar nuevo</a>
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
