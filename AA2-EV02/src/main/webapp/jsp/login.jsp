<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema de Inventarios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<div class="contenedor-login">
    <div class="tarjeta-login">

        <div class="login-logo">
            <h1>Sistema de Inventarios</h1>
            <p>Puertas y Ventanas Metálicas</p>
        </div>

        <%-- Mensaje de sesión expirada --%>
        <c:if test="${param.sesion eq 'expirada'}">
            <div class="alerta alerta-advertencia">
                Su sesión ha expirado. Por favor inicie sesión nuevamente.
            </div>
        </c:if>

        <%-- Mensaje de cierre de sesión exitoso --%>
        <c:if test="${param.logout eq 'true'}">
            <div class="alerta alerta-exito">
                Ha cerrado sesión correctamente.
            </div>
        </c:if>

        <%-- Mensaje de error de autenticación --%>
        <c:if test="${not empty error}">
            <div class="alerta alerta-error">
                ${error}
            </div>
        </c:if>

        <%-- Formulario de Login - Método POST --%>
        <form action="${pageContext.request.contextPath}/login" method="post">

            <div class="form-grupo">
                <label for="usuario">Usuario</label>
                <input type="text"
                       id="usuario"
                       name="usuario"
                       value="${usuarioIngresado}"
                       placeholder="Ingrese su usuario"
                       required
                       autofocus>
            </div>

            <div class="form-grupo">
                <label for="contrasena">Contraseña</label>
                <input type="password"
                       id="contrasena"
                       name="contrasena"
                       placeholder="Ingrese su contraseña"
                       required>
            </div>

            <div class="form-grupo" style="margin-top: 24px;">
                <button type="submit" class="btn btn-primario" style="width: 100%; padding: 11px;">
                    Ingresar al Sistema
                </button>
            </div>

        </form>

        <p style="text-align:center; color:#aaa; font-size:12px; margin-top:20px;">
            Usuarios de prueba: admin/admin123 &nbsp;|&nbsp; dfonseca/sena2026
        </p>

    </div>
</div>

</body>
</html>
