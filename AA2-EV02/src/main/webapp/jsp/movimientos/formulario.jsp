<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    pageContext.setAttribute("titulo", "Registrar Movimiento");
    pageContext.setAttribute("modulo", "movimientos");
%>
<jsp:include page="/jsp/includes/header.jsp" />
<jsp:include page="/jsp/includes/nav.jsp" />

<div class="contenedor">
    <div class="tarjeta">

        <h2 class="tarjeta-titulo">Registrar Movimiento de Inventario</h2>

        <%-- Mensaje de error --%>
        <c:if test="${not empty error}">
            <div class="alerta alerta-error">${error}</div>
        </c:if>

        <%-- Formulario de movimiento - Método POST --%>
        <form action="${pageContext.request.contextPath}/movimientos" method="post">

            <div class="form-grupo">
                <label for="materiaPrimaId">Material *</label>
                <select id="materiaPrimaId" name="materiaPrimaId" required
                        onchange="actualizarInfoMaterial(this)">
                    <option value="">-- Seleccione un material --</option>
                    <c:forEach var="mp" items="${materiales}">
                        <option value="${mp.id}"
                                data-stock="${mp.stockActual}"
                                data-unidad="${mp.unidad}"
                                data-estado="${mp.estado}"
                            ${materialPreseleccionado eq mp.id.toString() ? 'selected' : ''}>
                            ${mp.codigo} - ${mp.nombre}
                            (Stock: <fmt:formatNumber value="${mp.stockActual}" pattern="#,##0.##"/>
                            ${mp.unidad})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <%-- Panel de información del material seleccionado --%>
            <div id="infoMaterial" class="alerta alerta-info" style="display:none;">
                Stock actual: <strong id="stockActualInfo">-</strong>
                &nbsp;|&nbsp;
                Estado: <strong id="estadoInfo">-</strong>
            </div>

            <div class="fila-formulario">
                <div class="form-grupo">
                    <label for="tipo">Tipo de Movimiento *</label>
                    <select id="tipo" name="tipo" required>
                        <option value="ENTRADA">ENTRADA (Ingreso al inventario)</option>
                        <option value="SALIDA">SALIDA (Salida del inventario)</option>
                    </select>
                </div>
                <div class="form-grupo">
                    <label for="cantidad">Cantidad *</label>
                    <input type="number" id="cantidad" name="cantidad"
                           min="0.01" step="0.01"
                           placeholder="0.00"
                           required>
                </div>
            </div>

            <div class="form-grupo">
                <label for="observacion">Observación</label>
                <textarea id="observacion" name="observacion"
                          rows="3"
                          placeholder="Descripción del movimiento (opcional)..."></textarea>
            </div>

            <div style="display:flex; gap:12px; margin-top:10px;">
                <button type="submit" class="btn btn-primario">Registrar Movimiento</button>
                <a href="${pageContext.request.contextPath}/movimientos"
                   class="btn btn-secundario">Cancelar</a>
            </div>

        </form>
    </div>
</div>

<%-- Script para mostrar información del material seleccionado --%>
<script>
    function actualizarInfoMaterial(select) {
        var opcion = select.options[select.selectedIndex];
        var panel = document.getElementById('infoMaterial');

        if (opcion.value) {
            var stock = opcion.getAttribute('data-stock');
            var unidad = opcion.getAttribute('data-unidad');
            var estado = opcion.getAttribute('data-estado');

            document.getElementById('stockActualInfo').textContent = stock + ' ' + unidad;
            document.getElementById('estadoInfo').textContent = estado;
            panel.style.display = 'block';
        } else {
            panel.style.display = 'none';
        }
    }

    // Si hay un material preseleccionado, mostrar su información al cargar
    window.onload = function() {
        var select = document.getElementById('materiaPrimaId');
        if (select.value) actualizarInfoMaterial(select);
    };
</script>

<div class="footer">
    Sistema de Inventarios - GA7-220501096-AA2-EV02 &nbsp;|&nbsp; Diego Andrés Fonseca
</div>
</body>
</html>
