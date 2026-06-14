<%--
Autores:
- Andrea Pérez Rodríguez: 90%
- IA Generativa: 10%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Recogemos los parámetros inyectados dinámicamente con jsp:param por recomendación de la ia para no hardcodear
    String titulo = request.getParameter("titulo");
    String subtitulo = request.getParameter("subtitulo");

    // Asignamos valores por defecto por si alguna página falla
    if (titulo == null) {
        titulo = "Bancosol";
    }
    if (subtitulo == null) {
        subtitulo = "";
    }

    Boolean hayNoLeidas = (Boolean) request.getAttribute("hayNoLeidas");
    if (hayNoLeidas == null) {
        hayNoLeidas = false;
    }

%>

<header class="main-header">
    <nav>
        <link rel="stylesheet" href="/css/Header.css" />

        <div class="header-branding">
            <img src="/images/icono.png" alt="Bancosol" class="header-logo" />
            <div class="header-titles">
                <h1 id="dynamic-header-title"><%= titulo %></h1>
                <p id="dynamic-header-subtitle"><%= subtitulo %></p>
            </div>
        </div>

        <button class="btn-notificaciones" id="btn-notifications" onclick="window.location.href='/notificaciones'">
            Notificaciones
        </button>
    </nav>
</header>