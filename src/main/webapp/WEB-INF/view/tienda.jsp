<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica" %>
<%
    List<EstablecimientoDTO> tiendas = (List<EstablecimientoDTO>) request.getAttribute("tiendas");
    List<CadenaDTO> todasCadenas = (List<CadenaDTO>) request.getAttribute("todasCadenas");
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    List<ZonaGeografica> zonas = (List<ZonaGeografica>) request.getAttribute("zonas");
    EstablecimientoDTO tiendaSeleccionada = (EstablecimientoDTO) request.getAttribute("tiendaSeleccionada");
    String modoPanel = (String) request.getAttribute("modoPanel");
    String idCadena = (String) request.getAttribute("idCadena");
    String nombre = (String) request.getAttribute("nombre");
    String idCampana = (String) request.getAttribute("idCampana");
    String tipoVia = (String) request.getAttribute("tipoVia");
    String nombreVia = (String) request.getAttribute("nombreVia");
    String codigo = (String) request.getAttribute("codigo");
    String localidad = (String) request.getAttribute("localidad");
    Integer idZonaAttr = (Integer) request.getAttribute("idZona");
    String coordinador = (String) request.getAttribute("coordinador");

    if (modoPanel == null) modoPanel = "ninguno";
    if (idCadena == null) idCadena = "";
    if (nombre == null) nombre = "";
    if (idCampana == null) idCampana = "";
    if (tipoVia == null) tipoVia = "";
    if (nombreVia == null) nombreVia = "";
    if (codigo == null) codigo = "";
    if (localidad == null) localidad = "";
    if (coordinador == null) coordinador = "";

    String[] tiposVia = {"Calle", "Avenida", "Plaza"};
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tiendas</title>
    <link rel="stylesheet" href="../css/InformacionTienda.css">
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
            <jsp:param name="titulo" value="Tiendas" />
            <jsp:param name="subtitulo" value="Gestion de tiendas y establecimientos" />
        </jsp:include>

        <div class="main-layout">
            <main>
                <!--FILTROS-->
                <form action="/tiendas" method="GET">
                    <section class="filters">
                        <div class="filter-row">
                            <div class="filter-group">
                                <label for="filter-chain">Cadena</label>
                                <select id="filter-chain" name="idCadena">
                                    <option value="">Todas las cadenas</option>
                                    <% if (todasCadenas != null) {
                                        for (CadenaDTO c : todasCadenas) { %>
                                            <option value="<%= c.getIdCadena() %>" <%= idCadena.equals(c.getIdCadena()) ? "selected" : "" %>>
                                                <%= c.getNombreCadena() %>
                                            </option>
                                    <%  }
                                    } %>
                                </select>
                            </div>
                            <div class="filter-group">
                                <label for="filter-name">Nombre</label>
                                <input type="text" id="filter-name" name="nombre" value="<%= nombre %>" placeholder="Buscar por nombre" />
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
                            <div class="filter-group">
                                <label for="filter-zone">Zona</label>
                                <select id="filter-zone" name="idZona">
                                    <option value="">Todas las zonas</option>
                                    <% if (zonas != null) {
                                        for (ZonaGeografica z : zonas) { %>
                                            <option value="<%= z.getIdZona() %>" <%= idZonaAttr != null && idZonaAttr.equals(z.getIdZona()) ? "selected" : "" %>>
                                                <%= z.getNombreZona() %>
                                            </option>
                                    <%  }
                                    } %>
                                </select>
                            </div>
                            <div class="filter-group">
                                <label for="filter-coordinator">Coordinador</label>
                                <input type="text" id="filter-coordinator" name="coordinador" value="<%= coordinador %>" placeholder="Buscar por coordinador" />
                            </div>
                        </div>
                        <div class="filter-row">
                            <div class="filter-group filter-group--small">
                                <label for="filter-street-type">Tipo Vía</label>
                                <select id="filter-street-type" name="tipoVia">
                                    <option value="">Todos</option>
                                    <% for (String tv : tiposVia) { %>
                                        <option value="<%= tv %>" <%= tipoVia.equals(tv) ? "selected" : "" %>><%= tv %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="filter-group">
                                <label for="filter-street-name">Nombre Vía</label>
                                <input type="text" id="filter-street-name" name="nombreVia" value="<%= nombreVia %>" placeholder="Buscar por vía" />
                            </div>
                            <div class="filter-group filter-group--small">
                                <label for="filter-zip">Código Postal</label>
                                <input type="text" id="filter-zip" name="codigo" value="<%= codigo %>" placeholder="Buscar por CP" />
                            </div>
                            <div class="filter-group">
                                <label for="filter-locality">Localidad</label>
                                <input type="text" id="filter-locality" name="localidad" value="<%= localidad %>" placeholder="Buscar por localidad" />
                            </div>
                            <div class="filter-group filter-group--btn">
                                <button type="submit" id="btn-filter" class="btn btn--primary">Filtrar</button>
                            </div>
                        </div>
                    </section>
                </form>

                <div class="content-wrapper">
                    <div class="list-container">
                        <div class="list-header">
                            <h2>Listado de Tiendas</h2>
                            <form method="POST" action="/tiendas/anadir">
                                <button type="submit" id="btn-add" class="btn btn--primary">Añadir Tienda</button>
                            </form>
                            <p id="stores-counter"><%= tiendas != null ? tiendas.size() : 0 %> tiendas encontradas</p>
                        </div>

                        <div class="table-wrapper">
                            <table class="data-table">
                                <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Cadena</th>
                                    <th>Dirección</th>
                                    <th>Zona</th>
                                    <th>Coordinador</th>
                                </tr>
                                </thead>
                                <tbody id="stores-table">
                                <%
                                    if (tiendas != null && !tiendas.isEmpty()) {
                                        for (EstablecimientoDTO t : tiendas) {
                                    %>
                                    <tr<%= "detalle".equals(modoPanel) && tiendaSeleccionada != null && tiendaSeleccionada.getIdEstablecimiento() != null && tiendaSeleccionada.getIdEstablecimiento().equals(t.getIdEstablecimiento()) ? " class=\"selected\"" : "" %>>
                                        <td>
                                            <a href="/tiendas?idTienda=<%= t.getIdEstablecimiento() %><%= !idCadena.isEmpty() ? "&idCadena=" + java.net.URLEncoder.encode(idCadena, "UTF-8") : "" %><%= !nombre.isEmpty() ? "&nombre=" + java.net.URLEncoder.encode(nombre, "UTF-8") : "" %><%= !idCampana.isEmpty() ? "&idCampana=" + idCampana : "" %><%= !tipoVia.isEmpty() ? "&tipoVia=" + java.net.URLEncoder.encode(tipoVia, "UTF-8") : "" %><%= !nombreVia.isEmpty() ? "&nombreVia=" + java.net.URLEncoder.encode(nombreVia, "UTF-8") : "" %><%= !codigo.isEmpty() ? "&codigo=" + codigo : "" %><%= !localidad.isEmpty() ? "&localidad=" + java.net.URLEncoder.encode(localidad, "UTF-8") : "" %><%= idZonaAttr != null ? "&idZona=" + idZonaAttr : "" %><%= !coordinador.isEmpty() ? "&coordinador=" + java.net.URLEncoder.encode(coordinador, "UTF-8") : "" %>">
                                                <%= t.getNombreResena() %>
                                            </a>
                                        </td>
                                        <td><%= t.getNombreCadena() != null ? t.getNombreCadena() : "---" %></td>
                                        <td><%= t.getTipoVia() != null ? t.getTipoVia() + " " + t.getNombreVia() : "---" %></td>
                                        <td><%= t.getNombreZona() != null ? t.getNombreZona() : "---" %></td>
                                        <td><%= t.getCoordinadorNombre() != null ? t.getCoordinadorNombre() : "---" %></td>
                                    </tr>
                                <%      }
                                    } else { %>
                                    <tr>
                                        <td colspan="5" class="empty-state">No se encontraron tiendas.</td>
                                    </tr>
                                <%  } %>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <aside class="detail-panel">
                        <div class="detail-content">
                            <% if ("detalle".equals(modoPanel) && tiendaSeleccionada != null) { %>
                                <jsp:include page="detalleTienda.jsp" />
                            <% } else if ("editar".equals(modoPanel) && tiendaSeleccionada != null) { %>
                                <jsp:include page="editarTienda.jsp" />
                            <% } else if ("anadir".equals(modoPanel)) { %>
                                <jsp:include page="editarTienda.jsp" />
                            <% } else { %>
                                <div id="empty-state-panel" class="estado-vacio">
                                    <h3>Detalle de Tienda</h3>
                                    <p>Haga clic en una tienda de la lista para ver sus detalles.</p>
                                </div>
                            <% } %>
                        </div>

                        <% if ("detalle".equals(modoPanel) && tiendaSeleccionada != null) { %>
                        <div class="detail-actions-sticky">
                            <a href="/tiendas/editar?idTienda=<%= tiendaSeleccionada.getIdEstablecimiento() %>" class="btn btn--primary">Editar</a>
                            <a href="/tiendas/borrar?idTienda=<%= tiendaSeleccionada.getIdEstablecimiento() %>" class="btn btn--delete">Eliminar</a>
                        </div>
                        <% } %>
                        <% if ("editar".equals(modoPanel) || "anadir".equals(modoPanel)) { %>
                        <div class="detail-actions-sticky">
                            <button type="submit" form="tienda-form" class="btn btn--primary">Guardar</button>
                            <a href="/tiendas<%= tiendaSeleccionada != null && tiendaSeleccionada.getIdEstablecimiento() != null ? "?idTienda=" + tiendaSeleccionada.getIdEstablecimiento() : "" %>" class="btn btn--cancel">Cancelar</a>
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
