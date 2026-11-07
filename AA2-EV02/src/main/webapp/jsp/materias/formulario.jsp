<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    pageContext.setAttribute("titulo", "Registrar Material");
    pageContext.setAttribute("modulo", "materias");
%>
<jsp:include page="/jsp/includes/header.jsp" />
<jsp:include page="/jsp/includes/nav.jsp" />

<div class="contenedor">
    <div class="tarjeta">

        <%-- Titulo dinámico según si es creación o edición --%>
        <h2 class="tarjeta-titulo">
            <c:choose>
                <c:when test="${not empty material}">Editar Material</c:when>
                <c:otherwise>Nuevo Material</c:otherwise>
            </c:choose>
        </h2>

        <%-- Mensaje de error --%>
        <c:if test="${not empty error}">
            <div class="alerta alerta-error">${error}</div>
        </c:if>

        <%-- Formulario - Método POST para creación y actualización --%>
        <form action="${pageContext.request.contextPath}/materias" method="post">

            <%-- Campo oculto: acción (crear o actualizar) --%>
            <input type="hidden" name="accion"
                   value="${not empty material ? 'actualizar' : 'crear'}">

            <%-- Campo oculto: ID para edición --%>
            <c:if test="${not empty material}">
                <input type="hidden" name="id" value="${material.id}">
            </c:if>

            <div class="fila-formulario">
                <div class="form-grupo">
                    <label for="codigo">Código *</label>
                    <input type="text" id="codigo" name="codigo"
                           value="${material.codigo}"
                           placeholder="Ej: MP-001"
                           required maxlength="20">
                </div>
                <div class="form-grupo">
                    <label for="tipo">Tipo *</label>
                    <select id="tipo" name="tipo" required>
                        <option value="MATERIA_PRIMA"
                            ${material.tipo eq 'MATERIA_PRIMA' ? 'selected' : ''}>
                            Materia Prima
                        </option>
                        <option value="INSUMO"
                            ${material.tipo eq 'INSUMO' ? 'selected' : ''}>
                            Insumo
                        </option>
                    </select>
                </div>
            </div>

            <div class="form-grupo">
                <label for="nombre">Nombre del Material *</label>
                <input type="text" id="nombre" name="nombre"
                       value="${material.nombre}"
                       placeholder="Ej: Acero Galvanizado"
                       required maxlength="100">
            </div>

            <div class="form-grupo">
                <label for="unidad">Unidad de Medida *</label>
                <select id="unidad" name="unidad" required>
                    <option value="">Seleccione...</option>
                    <option value="kg" ${material.unidad eq 'kg' ? 'selected' : ''}>
                        Kilogramo (kg)
                    </option>
                    <option value="m" ${material.unidad eq 'm' ? 'selected' : ''}>
                        Metro (m)
                    </option>
                    <option value="m2" ${material.unidad eq 'm2' ? 'selected' : ''}>
                        Metro cuadrado (m2)
                    </option>
                    <option value="litro" ${material.unidad eq 'litro' ? 'selected' : ''}>
                        Litro
                    </option>
                    <option value="unidad" ${material.unidad eq 'unidad' ? 'selected' : ''}>
                        Unidad
                    </option>
                </select>
            </div>

            <div class="fila-formulario">
                <div class="form-grupo">
                    <label for="stockActual">Stock Actual *</label>
                    <input type="number" id="stockActual" name="stockActual"
                           value="${material.stockActual}"
                           min="0" step="0.01"
                           placeholder="0.00"
                           required>
                </div>
                <div class="form-grupo">
                    <label for="stockMinimo">Stock Mínimo *</label>
                    <input type="number" id="stockMinimo" name="stockMinimo"
                           value="${material.stockMinimo}"
                           min="0" step="0.01"
                           placeholder="0.00"
                           required>
                    <small style="color:#888; font-size:12px;">
                        El estado se calculará automáticamente según el stock.
                    </small>
                </div>
            </div>

            <div style="display:flex; gap:12px; margin-top:10px;">
                <button type="submit" class="btn btn-primario">
                    <c:choose>
                        <c:when test="${not empty material}">Guardar Cambios</c:when>
                        <c:otherwise>Registrar Material</c:otherwise>
                    </c:choose>
                </button>
                <a href="${pageContext.request.contextPath}/materias" class="btn btn-secundario">
                    Cancelar
                </a>
            </div>

        </form>
    </div>
</div>

<div class="footer">
    Sistema de Inventarios - GA7-220501096-AA2-EV02 &nbsp;|&nbsp; Diego Andrés Fonseca
</div>
</body>
</html>
