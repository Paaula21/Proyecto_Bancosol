<%--
Autores:
- Andrea Pérez Rodríguez: 100%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String error = (String)request.getAttribute("error");
%>

<html>
<head>
    <link rel="stylesheet" href="/css/Login.css">
    <title>Ventana de inicio</title>
</head>
<body>
<div class="main-container">
    <div class="div-image">
        <img src="images/icono.png" alt="Logo de BancoSol">
        <h1>Inicio de sesión</h1>
    </div>
    <fieldset class="login-container">
        <form method="POST" action="/autentica" class="form-login" id="form-login">
            <label for="username">Nombre de usuario</label><br>
            <input type="text" id="username" name="username" placeholder="Ej: Cristóbal" required autocomplete="username" /><br><br>

            <label for="password">Contraseña</label><br>
            <input type="password" id="password" name="password" required autocomplete="current-password" /><br><br>

            <br>
            <button type="submit">Inicio de sesión</button>
        </form>
    </fieldset>
    <!-- Esto muestra mensajes de error o éxito -->
    <%
        if (error != null) {
    %>
    <p id="message"><%= error %></p>
    <%
        }
    %>

</div>
</body>
</html>