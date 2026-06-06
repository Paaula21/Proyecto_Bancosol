<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Colaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.VistaColaboradoresDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.VistaColaboradores" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica" %>
<%
    List<VistaColaboradoresDTO> colaboradores = (List<VistaColaboradoresDTO>) request.getAttribute("colaboradores");
    List<ContactoColaborador> contacto = (List<ContactoColaborador>) request.getAttribute("ContactoColaborador");
    List<ZonaGeografica> zonasDisponibles = (List<ZonaGeografica>) request.getAttribute("zonasDisponibles");
    Colaborador seleccionado = (Colaborador) request.getAttribute("colaboradorSeleccionado");
    ContactoColaborador contactoSeleccionado = (ContactoColaborador) request.getAttribute("contactoSeleccionado");
    String modoPanel = (String) request.getAttribute("modoPanel");
    String busqueda = (String) request.getAttribute("busqueda");
    String zona = (String) request.getAttribute("zona");

    if (modoPanel == null) modoPanel = "ninguno";
    if (busqueda == null) busqueda = "";
    if (zona == null) zona = "Todas";
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Gestión de Colaboradores</title>
    <link rel="stylesheet" href="/css/Common.css">
    <link rel="stylesheet" href="/css/Colaboradores.css">
    <link rel="stylesheet" href="/css/Sidebar.css">
    <link rel="stylesheet" href="/css/DetalleColaborador.css">
    <link rel="stylesheet" href="/css/EditarAnadirColaborador.css">
    <link rel="stylesheet" href="/css/Header.css">
</head>

<body>
<div class="main-layout">
    <aside class="sidebar">
        <jsp:include page="Sidebar.jsp" />
    </aside>
    <div class="right-content">
        <header class="main-header">
            <jsp:include page="Header.jsp" />
        </header>
        <main>
            <form action="/colaboradores" method="GET" style="margin: 0; padding: 0; display: contents;">
                <section class="filters">
                    <div class="filter-group">
                        <label>Zona Geográfica</label>
                        <select id="filter-zona" name="zona">
                            <option value="Todas" <%= zona.equals("Todas") ? "selected" : "" %>>Todas</option>
                            <%
                                if (zonasDisponibles != null) {
                                    for (ZonaGeografica z : zonasDisponibles) {
                            %>
                            <option value="<%= z.getNombreZona() %>" <%= zona.equals(z.getNombreZona()) ? "selected" : "" %>>
                                <%= z.getNombreZona() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label>Buscar</label>
                        <input type="text" id="input-search" name="busqueda" value="<%= busqueda %>" placeholder="Nombre o código del colaborador...">
                    </div>
                    <div class="filter-button">
                        <button type="submit" class="btn btn--primary" id="btn-filter">Filtrar</button>
                    </div>
                </section>
            </form>

            <hr>
            <div class="content-wrapper">
                <section class="list-container">
                    <header class="list-header">
                        <h2>Listado de Colaboradores</h2>
                        <p id="contador-colaboradores"><%= colaboradores != null ? colaboradores.size() : 0 %> colaboradores encontrados</p>
                        <button type="button" class="btn btn--primary" id="btn-abrir-registro" onclick="window.location.href='/colaboradores?accion=nuevo'">
                            Añadir Colaborador
                        </button>
                    </header>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Colaborador</th>
                                <th>Localidad</th>
                                <th>Zona</th>
                                <th>Contacto</th>
                            </tr>
                            </thead>
                            <tbody id="tabla-colaboradores">
                            <%
                                if (colaboradores != null && !colaboradores.isEmpty()) {
                                    for (VistaColaboradoresDTO c : colaboradores) {
                                        // Al venir del DTO, simplemente comprobamos si son nulos para dar un valor por defecto
                                        String localidad = c.getNombreDivision() != null ? c.getNombreDivision() : "Sin asignar";
                                        String zonaCol = c.getNombreZona() != null ? c.getNombreZona() : "Sin asignar";
                                        String contactoStr = c.getNombreContacto() != null ? c.getNombreContacto() : "Sin contacto";
                            %>
                            <tr>
                                <td>
                                    <a href="/colaboradores?id=<%= c.getIdColaborador() %>" style="color: inherit; text-decoration: none; font-weight: bold;">
                                        <%= c.getNombreColaborador() %>
                                    </a>
                                </td>
                                <td><%= localidad %></td>
                                <td><%= zonaCol %></td>
                                <td><%= contactoStr %></td>
                            </tr>
                            <%      }
                            } else {
                            %>
                            <tr>
                                <td colspan="4" style="text-align: center; padding: 20px;">No se encontraron colaboradores.</td>
                            </tr>
                            <%  } %>
                            </tbody>
                        </table>
                    </div>
                </section>
                <aside class="detail-panel">
                    <div class="detail-content">
                        <% if ("detalle".equals(modoPanel) && seleccionado != null) { %>
                        <jsp:include page="DetalleColaborador.jsp" />
                        <% } else if ("editar".equals(modoPanel) && seleccionado != null) { %>
                        <jsp:include page="EditarColaboradores.jsp" />
                        <% } else if ("anadir".equals(modoPanel)) { %>
                        <jsp:include page="AnadirColaboradores.jsp" />
                        <% } else { %>
                        <div style="text-align: center; padding: 40px; color: #888;">Selecciona un colaborador para ver sus detalles.</div>
                        <% } %>
                    </div>

                    <div class="detail-actions-sticky" id="acciones-detalle-colaborador" style="<%= "detalle".equals(modoPanel) ? "display: flex;" : "display: none;" %>">
                        <button type="button" id="btn-editar-colaborador" class="btn btn--primary" onclick="window.location.href='/colaboradores?id=<%= seleccionado != null ? seleccionado.getIdColaborador() : "" %>&accion=editar'">
                            Editar Colaborador
                        </button>
                        <form action="/colaboradores/eliminar" method="POST" style="display: contents;">
                            <input type="hidden" name="id" value="<%= seleccionado != null ? seleccionado.getIdColaborador() : "" %>">
                            <button type="submit" id="btn-eliminar-colaborador" class="btn btn--delete" onclick="return confirm('¿Estás seguro de que deseas eliminar este colaborador?');">
                                Eliminar
                            </button>
                        </form>
                    </div>

                    <div class="detail-actions-sticky" id="acciones-edicion-colaborador" style="<%= "editar".equals(modoPanel) ? "display: flex;" : "display: none;" %>">
                        <button type="submit" form="form-edicion-colaborador" id="btn-guardar-cambios" class="btn btn--primary">
                            Guardar Cambios
                        </button>
                        <button type="button" id="btn-cancelar-edicion" class="btn btn--cancel" onclick="window.location.href='/colaboradores?id=<%= seleccionado != null ? seleccionado.getIdColaborador() : "" %>'">
                            Cancelar
                        </button>
                    </div>

                    <div class="detail-actions-sticky" id="acciones-anadir-colaborador" style="<%= "anadir".equals(modoPanel) ? "display: flex;" : "display: none;" %>">
                        <button type="submit" form="form-nuevo-colaborador-lateral" id="btn-guardar-nuevo" class="btn btn--primary">
                            Guardar Colaborador
                        </button>
                        <button type="button" id="btn-cancelar-nuevo" class="btn btn--cancel" onclick="window.location.href='/colaboradores'">
                            Cancelar
                        </button>
                    </div>
                </aside>
            </div>
        </main>
    </div>
</div>

<div class="overlay" id="overlay-eliminar">
    <div class="popup" id="popup-eliminar">
        <h3>¿Eliminar Colaborador?</h3>
        <p>Esta acción no se puede deshacer. ¿Estás seguro de que deseas eliminar a este colaborador de la base de
            datos?</p>

        <div class="popup-actions">
            <form action="">
                <button class="btn-add btn-rojo" id="btn-confirmar-eliminar">Eliminar</button>
                <button class="btn-cerrar-popup" id="btn-cancelar-eliminar">Cancelar</button>
            </form>
        </div>
    </div>
</div>
</body>

</html>