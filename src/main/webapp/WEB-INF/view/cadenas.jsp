<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%
    List<CadenaDTO> cadenas = (List<CadenaDTO>) request.getAttribute("cadenas");
    List<CadenaDTO> todasCadenas = (List<CadenaDTO>) request.getAttribute("todasCadenas");
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    CadenaDTO cadenaSeleccionada = (CadenaDTO) request.getAttribute("cadenaSeleccionada");
    String modoPanel = (String) request.getAttribute("modoPanel");
    String nombre = (String) request.getAttribute("nombre");
    String idCampana = (String) request.getAttribute("idCampana");

    if (modoPanel == null) modoPanel = "ninguno";
    if (nombre == null) nombre = "";
    if (idCampana == null) idCampana = "";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Cadenas</title>
        <link rel="stylesheet" href="../css/InformacionCadena.css">
        <link rel="stylesheet" href="../css/TablaEstilos.css">
        <link rel="stylesheet" href="../css/Common.css">
        <link rel="stylesheet" href="../css/Sidebar.css">
        <link rel="stylesheet" href="../css/popUpRegistro.css">
        <link rel="stylesheet" href="../css/Header.css">
        <link rel="stylesheet" href="../css/DetalleColaborador.css">
        <link rel="stylesheet" href="../css/EditarAnadirColaborador.css">
    </head>
    <body>
    <div class="app-container">
        <jsp:include page="sidebar.jsp" />
        <div class="right-content">
            <jsp:include page="header.jsp">
                <jsp:param name="titulo" value="Cadenas" />
                <jsp:param name="subtitulo" value="Gestion de cadenas y establecimientos" />
            </jsp:include>

            <div class="main-layout">
                <main>
                    <!--FILTROS-->
                    <form action="/cadenas" method="GET">
                        <section class="filters">
                            <div class="filter-group">
                                <label for="filter-chain">Nombre</label>
                                <select id="filter-chain" name="nombre">
                                    <option value="">Todas las cadenas</option>
                                    <% if (todasCadenas != null) {
                                        for (CadenaDTO c : todasCadenas) { %>
                                            <option value="<%= c.getNombreCadena() %>" <%= nombre.equals(c.getNombreCadena()) ? "selected" : "" %>>
                                                <%= c.getNombreCadena() %>
                                            </option>
                                    <%  }
                                    } %>
                                </select>
                            </div>
                            <div class="filter-group">
                                <label for="filter-campaign">Campaña</label>
                                <select id="filter-campaign" name="idCampana">
                                    <option value="">Todas las campañas</option>
                                    <% if (campanas != null) {
                                        for (CampanaDTO camp : campanas) { %>
                                            <option value="<%= camp.getIdCampana() %>" <%= idCampana.equals(camp.getIdCampana()) ? "selected" : "" %>>
                                                <%= camp.getNombreCampana() %>
                                            </option>
                                    <%  }
                                    } %>
                                </select>
                            </div>
                            <div class="filter-button">
                                <button type="submit" id="btn-filter" class="btn btn--primary">Filtrar</button>
                            </div>
                        </section>
                    </form>

                    <div class="content-wrapper">
                        <div class="list-container">
                            <div class="list-header">
                                <h2>Listado de Cadenas</h2>
                                <form method="POST" action="/cadenas/anadir">
                                    <button type="submit" id="btn-add" class="btn btn--primary">Añadir Cadena</button>
                                </form>
                                <p id="chains-counter"><%= cadenas != null ? cadenas.size() : 0 %> cadenas encontradas</p>
                            </div>

                            <div class="table-wrapper">
                                <table class="data-table">
                                    <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Nº Establecimientos</th>
                                    </tr>
                                    </thead>
                                    <tbody id="chains-table">
                                    <%
                                        if (cadenas != null && !cadenas.isEmpty()) {
                                            for (CadenaDTO c : cadenas) {
                                        %>
                                        <tr<%= "detalle".equals(modoPanel) && cadenaSeleccionada != null && cadenaSeleccionada.getIdCadena() != null && cadenaSeleccionada.getIdCadena().equals(c.getIdCadena()) ? " class=\"selected\"" : "" %>>
                                            <td>
                                                <a href="/cadenas?idCadena=<%= c.getIdCadena() %><%= !nombre.isEmpty() ? "&nombre=" + java.net.URLEncoder.encode(nombre, "UTF-8") : "" %><%= !idCampana.isEmpty() ? "&idCampana=" + idCampana : "" %>">
                                                    <%= c.getNombreCadena() %>
                                                </a>
                                            </td>
                                            <td><%= c.getNumEstablecimientos() %></td>
                                        </tr>
                                    <%      }
                                        } else { %>
                                        <tr>
                                            <td colspan="2" class="empty-state">No se encontraron cadenas.</td>
                                        </tr>
                                    <%  } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <aside class="detail-panel">
                            <div class="detail-content">
                                <% if ("detalle".equals(modoPanel) && cadenaSeleccionada != null) { %>
                                    <jsp:include page="detalleCadena.jsp" />
                                <% } else if ("editar".equals(modoPanel) && cadenaSeleccionada != null) { %>
                                    <jsp:include page="editarCadena.jsp" />
                                <% } else if ("anadir".equals(modoPanel)) { %>
                                    <jsp:include page="editarCadena.jsp" />
                                <% } else { %>
                                    <div id="empty-state-panel" class="estado-vacio">
                                        <h3>Detalle de Cadena</h3>
                                        <p>Haga clic en una cadena de la lista para ver sus detalles.</p>
                                    </div>
                                <% } %>
                            </div>

                            <% if ("detalle".equals(modoPanel) && cadenaSeleccionada != null) { %>
                            <div class="detail-actions-sticky">
                                <a href="/cadenas/editar?idCadena=<%= cadenaSeleccionada.getIdCadena() %>" class="btn btn--primary">Editar</a>
                                <a href="/cadenas/borrar?idCadena=<%= cadenaSeleccionada.getIdCadena() %>" class="btn btn--delete">Eliminar</a>
                            </div>
                            <% } %>
                            <% if ("editar".equals(modoPanel) || "anadir".equals(modoPanel)) { %>
                            <div class="detail-actions-sticky">
                                <button type="submit" form="chain-form" class="btn btn--primary">Guardar</button>
                                <a href="/cadenas<%= cadenaSeleccionada != null && cadenaSeleccionada.getIdCadena() != null ? "?idCadena=" + cadenaSeleccionada.getIdCadena() : "" %>" class="btn btn--cancel">Cancelar</a>
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
