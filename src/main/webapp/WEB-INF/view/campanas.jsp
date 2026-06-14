<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO" %>
<%
    List<CampanaDTO> listaCampanas = (List<CampanaDTO>) request.getAttribute("campanas");
    CampanaDTO campanaSeleccionada = (CampanaDTO) request.getAttribute("campanaSeleccionada");
    String modoPanel = (String) request.getAttribute("modoPanel");
    String estadoFiltro = (String) request.getAttribute("estadoFiltro");
    String busquedaFiltro = (String) request.getAttribute("busquedaFiltro");

    if (modoPanel == null) modoPanel = "ninguno";
    if (estadoFiltro == null) estadoFiltro = "Todos";
    if (busquedaFiltro == null) busquedaFiltro = "";

    // Obtenemos el rol del usuario para los permisos
    UsuarioDTO user = (UsuarioDTO) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vista de campañas</title>

    <link rel="stylesheet" href="/css/Common.css">
    <link rel="stylesheet" href="/css/Campana.css">
    <link rel="stylesheet" href="/css/Sidebar.css">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/DetalleColaborador.css">
    <link rel="stylesheet" href="/css/EditarAnadirColaborador.css">
    <link rel="stylesheet" href="/css/EditarCampana.css">
</head>

<body>
<div class="app-container">
    <jsp:include page="sidebar.jsp" />

    <div class="right-content">
        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Campañas" />
            <jsp:param name="subtitulo" value="Gestión y planificación de campañas activas" />
        </jsp:include>

        <div class="main-layout">
            <main>
                <!-- Formulario GET para los filtros -->
                <form action="/campanas" method="GET">
                    <section class="filters">
                        <div class="filter-group">
                            <label>Estado</label>
                            <select name="estado">
                                <option value="Todos" <%= "Todos".equals(estadoFiltro) ? "selected" : "" %>>Todos</option>
                                <option value="planificada" <%= "planificada".equals(estadoFiltro) ? "selected" : "" %>>Planificada</option>
                                <option value="activa" <%= "activa".equals(estadoFiltro) ? "selected" : "" %>>Activa</option>
                                <option value="finalizada" <%= "finalizada".equals(estadoFiltro) ? "selected" : "" %>>Finalizada</option>
                                <option value="cancelada" <%= "cancelada".equals(estadoFiltro) ? "selected" : "" %>>Cancelada</option>
                            </select>
                        </div>

                        <div class="filter-group">
                            <label>Buscar</label>
                            <input type="text" name="busqueda" value="<%= busquedaFiltro %>" placeholder="Nombre de campaña...">
                        </div>

                        <div class="filter-button">
                            <button type="submit" class="btn btn--primary">Filtrar</button>
                        </div>
                    </section>
                </form>

                <div class="content-wrapper">
                    <div class="list-container">
                        <div class="list-header">
                            <div class="header-titles">
                                <h2>Listado de Campañas</h2>
                                <p id="total-campanas">
                                    <%= listaCampanas != null ? listaCampanas.size() : 0 %> campañas encontradas
                                </p>
                            </div>

                            <% if (user.getIdRol() == 1) { %>
                            <div class="btn-history-add">
                                <button type="button" class="btn btn--primary" onclick="window.location.href='/historial'">
                                    Ver historial
                                </button>
                                <button type="button" class="btn btn--primary" onclick="window.location.href='/campanas?accion=nuevo'">
                                    Añadir campaña
                                </button>
                            </div>
                            <% } %>

                        </div>

                        <div class="table-wrapper">
                            <table class="data-table">
                                <thead>
                                <tr>
                                    <th>Campaña</th>
                                    <th>Fecha Inicio</th>
                                    <th>Fecha Fin</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    if (listaCampanas != null && !listaCampanas.isEmpty()) {
                                        for (CampanaDTO c : listaCampanas) {
                                            boolean isSelected = campanaSeleccionada != null && c.getIdCampana().equals(campanaSeleccionada.getIdCampana());
                                %>
                                <tr class="<%= isSelected ? "selected" : "" %>">
                                    <td>
                                        <a href="/campanas?id=<%= c.getIdCampana() %>&estado=<%= estadoFiltro %>&busqueda=<%= busquedaFiltro %>" style="color: inherit; text-decoration: none; font-weight: bold;">
                                            <%= c.getNombreCampana() %>
                                        </a>
                                    </td>
                                    <td><%= c.getFechaInicio() != null ? c.getFechaInicio() : "-" %></td>
                                    <td><%= c.getFechaFin() != null ? c.getFechaFin() : "-" %></td>
                                    <td>
                                        <span class="badge <%= "Activa".equalsIgnoreCase(c.getEstado()) ? "badge--success" : "badge--secondary" %>">
                                            <%= c.getEstado() != null ? c.getEstado() : "Sin estado" %>
                                        </span>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn--cancel" onclick="window.location.href='/campanas/turnos?id=<%= c.getIdCampana() %>'">
                                            Asignar turnos
                                        </button>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="5" class="empty-state">
                                        No hay ninguna campaña que coincida con los filtros.
                                    </td>
                                </tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <aside class="detail-panel">
                        <div class="detail-content">
                            <% if ("detalle".equals(modoPanel) && campanaSeleccionada != null) { %>
                            <jsp:include page="detalleCampana.jsp" />
                            <% } else if (("editar".equals(modoPanel) && campanaSeleccionada != null) || "anadir".equals(modoPanel)) { %>
                            <jsp:include page="editarCampana.jsp" />
                            <% } else { %>
                            <div class="estado-vacio">
                                <h3>Detalle de la Campaña</h3>
                                <p>Selecciona una campaña para ver sus detalles.</p>
                            </div>
                            <% } %>
                        </div>

                        <% if ("detalle".equals(modoPanel) && campanaSeleccionada != null) { %>

                        <% if (user.getIdRol() == 1) { %>
                        <div class="detail-actions-sticky">
                            <a href="/campanas?id=<%= campanaSeleccionada.getIdCampana() %>&accion=editar" class="btn btn--primary">Editar Campaña</a>
                            <form action="/campanas/eliminar" method="POST" style="display: contents;">
                                <input type="hidden" name="id" value="<%= campanaSeleccionada.getIdCampana() %>">
                                <button type="submit" class="btn btn--delete">
                                    Eliminar
                                </button>
                            </form>
                        </div>
                        <% } %>

                        <% } else if ("editar".equals(modoPanel) || "anadir".equals(modoPanel)) { %>
                        <%-- Esta pantalla de edición/creación solo la alcanzan los roles 1 y 2 de todos modos --%>
                        <div class="detail-actions-sticky">
                            <button type="submit" form="form-campana" class="btn btn--primary">
                                Guardar Cambios
                            </button>
                            <a href="/campanas<%= campanaSeleccionada != null ? "?id=" + campanaSeleccionada.getIdCampana() : "" %>" class="btn btn--cancel">Cancelar</a>
                        </div>
                        <% } %>
                    </aside>
                </div>
            </main>
        </div>
    </div>
</div>
</body>
</html>