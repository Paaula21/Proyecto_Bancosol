<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%
    List<CampanaDTO> listaCampanas = (List<CampanaDTO>) request.getAttribute("campanas");
    CampanaDTO campanaSeleccionada = (CampanaDTO) request.getAttribute("campanaSeleccionada");
    String modoPanel = (String) request.getAttribute("modoPanel");
    String estadoFiltro = (String) request.getAttribute("estadoFiltro");
    String busquedaFiltro = (String) request.getAttribute("busquedaFiltro");

    if (modoPanel == null) modoPanel = "ninguno";
    if (estadoFiltro == null) estadoFiltro = "Todos";
    if (busquedaFiltro == null) busquedaFiltro = "";
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
<div class="main-layout">
    <jsp:include page="sidebar.jsp" />

    <div class="right-content">
        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Campañas" />
            <jsp:param name="subtitulo" value="Gestión y planificación de campañas activas" />
        </jsp:include>

        <main>
            <!-- Formulario GET para los filtros -->
            <form action="/campanas" method="GET" class="filter-form">
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
                <section class="list-container">
                    <header class="list-header">
                        <div class="header-titles">
                            <h2>Listado de Campañas</h2>
                            <p id="total-campanas">
                                <%= listaCampanas != null ? listaCampanas.size() : 0 %> campañas encontradas
                            </p>
                        </div>
                        <div class="btn-history-add">
                            <button type="button" class="btn btn--primary" onclick="window.location.href='/historial'">
                                Ver historial
                            </button>
                            <button type="button" class="btn btn--primary" onclick="window.location.href='/campanas?accion=nuevo'">
                                Añadir campaña
                            </button>
                        </div>
                    </header>

                    <div class="table-wrapper">
                        <table>
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
                            <tr style="<%= isSelected ? "background-color: #f5f0ff;" : "" %>">
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
                                <td colspan="5">
                                    No hay ninguna campaña que coincida con los filtros.
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </section>

                <aside class="detail-panel">
                    <div class="detail-content">
                        <% if ("detalle".equals(modoPanel) && campanaSeleccionada != null) { %>
                        <jsp:include page="detalleCampana.jsp" />
                        <% } else if (("editar".equals(modoPanel) && campanaSeleccionada != null) || "anadir".equals(modoPanel)) { %>
                        <jsp:include page="editarCampana.jsp" />
                        <% } else { %>
                        <div class="select-campain">Selecciona una campaña para ver sus detalles.</div>
                        <% } %>
                    </div>

                    <% if ("detalle".equals(modoPanel) && campanaSeleccionada != null) { %>
                    <div class="detail-actions-sticky">
                        <button type="button" class="btn btn--primary" onclick="window.location.href='/campanas?id=<%= campanaSeleccionada.getIdCampana() %>&accion=editar'">
                            Editar Campaña
                        </button>
                        <form action="/campanas/eliminar" method="POST" style="display: contents;">
                            <input type="hidden" name="id" value="<%= campanaSeleccionada.getIdCampana() %>">
                            <button type="submit" class="btn btn--delete" onclick="return confirm('¿Estás seguro de que deseas eliminar esta campaña de forma permanente?');">
                                Eliminar
                            </button>
                        </form>
                    </div>
                    <% } else if ("editar".equals(modoPanel) || "anadir".equals(modoPanel)) { %>
                    <div class="detail-actions-sticky">
                        <button type="submit" form="form-campana" class="btn btn--primary">
                            Guardar Cambios
                        </button>
                        <button type="button" class="btn btn--cancel" onclick="window.location.href='/campanas<%= campanaSeleccionada != null ? "?id=" + campanaSeleccionada.getIdCampana() : "" %>'">
                            Cancelar
                        </button>
                    </div>
                    <% } %>
                </aside>
            </div>
        </main>
    </div>
</div>
</body>
</html>