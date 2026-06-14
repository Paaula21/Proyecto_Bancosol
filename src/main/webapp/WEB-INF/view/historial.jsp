<%--
Página JSP que muestra el historial de todas las acciones realizadas.
- Ainhoa García Rebollo: 100%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.LogCampana" %>
<%
    // Recuperamos la lista de logs que el controlador ha enviado
    List<LogCampana> logs = (List<LogCampana>) request.getAttribute("logs");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Acciones</title>

    <link rel="stylesheet" href="../css/Historial.css">
    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/Common.css">
</head>
<body>

<div class="app-container">

    <jsp:include page="sidebar.jsp" />

    <div class="right-content">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Historial" />
            <jsp:param name="subtitulo" value="Registro de actividades y eventos pasados" />
        </jsp:include>

        <div class="main-layout">
            <main>

                <div class="historial-container">

                    <div class="historial-header">
                        <h2 class="historial-titulo">Registro de Acciones</h2>
                    </div>

                    <ul class="historial-lista">
                        <%
                            if (logs != null && !logs.isEmpty()) {
                                for (LogCampana log : logs) {
                        %>
                        <li class="historial-item">
                            <div class="historial-info">
                                <span class="historial-accion">Campaña <%= log.getCampaignName() %> se ha <%= log.getAction() %></span>
                            </div>
                            <div class="historial-fecha">
                                <%= log.getTimestamp() != null ? log.getTimestamp().toString().substring(0, 19).replace("T", " a las ") : "" %>
                            </div>
                        </li>
                        <%
                            }
                        } else {
                        %>
                        <li class="historial-item">
                            <div class="historial-mensaje">No hay ningún registro en el historial.</div>
                        </li>
                        <%
                            }
                        %>
                    </ul>

                </div>
            </main>
        </div>
    </div>
</div>

</body>
</html>