<%--
Página JSP que muestra la vista de las notificaciones
Autores:
- Andrea Pérez Rodríguez: 95%
- IA Generativa: 5%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.NotificacionDTO" %>
<%
    List<NotificacionDTO> notificaciones = (List<NotificacionDTO>) request.getAttribute("notificaciones");
    NotificacionDTO seleccionada = (NotificacionDTO) request.getAttribute("seleccionada");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Notificaciones</title>
    <link rel="stylesheet" href="/css/Common.css">
    <link rel="stylesheet" href="/css/Campana.css">
    <link rel="stylesheet" href="/css/Sidebar.css">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/Notificaciones.css">
    <link rel="stylesheet" href="/css/popUpRegistro.css">
</head>
<body>
<div class="app-container">
    <jsp:include page="sidebar.jsp" />

    <div class="right-content">
        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Notificaciones" />
            <jsp:param name="subtitulo" value="Tus avisos y mensajes del sistema" />
        </jsp:include>

        <main class="campana-container notificaciones-main">
            <div class="content-wrapper">

                <!-- LISTA IZQUIERDA -->
                <section class="list-container">
                    <header class="list-header">
                        <h2>Listado de Avisos</h2>
                        <p>Total: <%= notificaciones != null ? notificaciones.size() : 0 %> notificaciones</p>
                    </header>

                    <% if (notificaciones == null || notificaciones.isEmpty()) { %>
                    <div class="mensaje-vacio">
                        <p>No tienes notificaciones nuevas en este momento.</p>
                    </div>
                    <% } else { %>
                    <div class="notificaciones-list">
                        <% for (NotificacionDTO n : notificaciones) {
                            boolean isSelected = (seleccionada != null && seleccionada.getIdNotificacion().equals(n.getIdNotificacion()));
                        %>
                        <div onclick="window.location.href='/notificaciones?idNotificacion=<%= n.getIdNotificacion() %>'"
                             class="notificacion-item <%= isSelected ? "selected" : "" %> <%= !n.getLeida() ? "unread" : "" %>">

                            <div>
                                <h4 class="notif-title"><%= n.getTitulo() %></h4>
                                <span class="notif-type"><%= n.getIdTipo() %></span>
                            </div>
                            <div class="notif-date-container">
                                    <span class="notif-date">
                                        <%= n.getFechaCreacion() != null ? n.getFechaCreacion().toString() : "" %>
                                    </span>
                                <% if (!n.getLeida()) { %>
                                <span class="notif-new-badge">Nueva</span>
                                <% } %>
                            </div>
                        </div>
                        <% } %>
                    </div>
                    <% } %>
                </section>

                <!-- PANEL DERECHO (DETALLE) -->
                <aside class="detail-panel">
                    <div class="detail-content">
                        <% if (seleccionada == null) { %>
                        <div class="notif-empty-state">
                            <h3>Detalle de la Notificación</h3>
                            <p>Haz clic en una notificación de la lista para leer el mensaje.</p>
                        </div>
                        <% } else { %>
                        <div class="notif-detail-container">
                            <h3 class="notif-detail-header">Mensaje</h3>

                            <div class="notif-detail-title-wrapper">
                                <h4 class="notif-detail-title"><%= seleccionada.getTitulo() %></h4>
                                <span class="notif-detail-badge"><%= seleccionada.getIdTipo() %></span>
                            </div>

                            <div class="notif-card">
                                <h5>Fecha de recepción</h5>
                                <p><%= seleccionada.getFechaCreacion() != null ? seleccionada.getFechaCreacion().toString() : "" %></p>
                            </div>

                            <div class="notif-card">
                                <h5>Contenido</h5>
                                <p class="content-text"><%= seleccionada.getMensaje() %></p>
                            </div>

                            <div class="notif-actions">
                                <form action="/notificaciones/borrar" method="POST">
                                    <input type="hidden" name="id" value="<%= seleccionada.getIdNotificacion() %>">
                                    <button type="submit" class="btn-notif-delete">
                                        Borrar notificación
                                    </button>
                                </form>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </aside>
            </div>
        </main>
    </div>
</div>

</body>
</html>