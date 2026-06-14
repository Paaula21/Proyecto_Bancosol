<!--
Página JSP que muestra el listado completo de los colaboradores con o sin filtrado y permite realizar las diferentes acciones añadidas
Autores:
- Paula Fernández Jiménez: 100%
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Colaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.VistaColaboradores" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica" %>
<%
    List<ColaboradorDTO> colaboradores = (List<ColaboradorDTO>) request.getAttribute("colaboradores");
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
        <jsp:include page="sidebar.jsp" />
    <div class="right-content">
        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Colaboradores" />
            <jsp:param name="subtitulo" value="Gestión de entidades colaboradoras" />
        </jsp:include>
        <main>
            <form action="/colaboradores" method="GET">
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
                        <form action="/colaboradores" method="get">
                            <input type="hidden" name="accion" value="nuevo">
                            <button type="submit" class="btn btn--primary" id="btn-abrir-registro">
                                Añadir Colaborador
                            </button>
                        </form>
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
                                    for (ColaboradorDTO c : colaboradores) {
                                        // Al venir del DTO, simplemente comprobamos si son nulos para dar un valor por defecto
                                        String localidad = c.getNombreDivision() != null ? c.getNombreDivision() : "Sin asignar";
                                        String zonaCol = c.getNombreZona() != null ? c.getNombreZona() : "Sin asignar";
                                        String contactoStr = c.getNombreContacto() != null ? c.getNombreContacto() : "Sin contacto";
                            %>
                            <tr>
                                <td>
                                    <a href="/colaboradores?id=<%= c.getIdColaborador() %>" class="colabName">
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
                                <td colspan="4">No se encontraron colaboradores.</td>
                            </tr>
                            <%  } %>
                            </tbody>
                        </table>
                    </div>
                </section>
                <aside class="detail-panel">
                    <div class="detail-content">
                        <% if ("detalle".equals(modoPanel) && seleccionado != null) { %>
                        <jsp:include page="detalleColaborador.jsp" />
                        <% } else if ("editar".equals(modoPanel) && seleccionado != null) { %>
                        <jsp:include page="editarColaboradores.jsp" />
                        <% } else if ("anadir".equals(modoPanel)) { %>
                        <jsp:include page="anadirColaboradores.jsp" />
                        <% } else { %>
                        <div class='infoSide'>Selecciona un colaborador para ver sus detalles.</div>
                        <% } %>
                    </div>

                    <div class="detail-actions-sticky" id="acciones-detalle-colaborador" style="<%= "detalle".equals(modoPanel) ? "display: flex;" : "display: none;" %>">
                        <form action="/colaboradores" method="get" class="pos-content">
                            <input type="hidden" name="id" value="<%= seleccionado != null ? seleccionado.getIdColaborador() : "" %>">
                            <input type="hidden" name="accion" value="editar">
                            <button type="submit" id="btn-editar-colaborador" class="btn btn--primary">
                                Editar Colaborador
                            </button>
                        </form>
                        <form action="/colaboradores/eliminar" method="post" class="pos-content">
                            <input type="hidden" name="id" value="<%= seleccionado != null ? seleccionado.getIdColaborador() : "" %>">
                            <button type="submit" id="btn-eliminar-colaborador" class="btn btn--delete">
                                Eliminar
                            </button>
                        </form>
                    </div>

                    <div class="detail-actions-sticky" id="acciones-edicion-colaborador" style="<%= "editar".equals(modoPanel) ? "display: flex;" : "display: none;" %>">
                        <!--Empleamos el atributo form, ya que el botón pertenece a un formulario que no se encuentra en este archivo-->
                        <button type="submit" form="form-edicion-colaborador" id="btn-guardar-cambios" class="btn btn--primary pos-content">
                            Guardar Cambios
                        </button>
                        <form action="/colaboradores" method="get" class="pos-content">
                            <button type="submit" id="btn-cancelar-nuevo" class="btn btn--cancel">
                                Cancelar
                            </button>
                        </form>
                    </div>

                    <div class="detail-actions-sticky" id="acciones-anadir-colaborador" style="<%= "anadir".equals(modoPanel) ? "display: flex;" : "display: none;" %>">
                        <button type="submit" form="form-nuevo-colaborador-lateral" id="btn-guardar-nuevo" class="btn btn--primary pos-content">
                            Guardar Colaborador
                        </button>
                        <form action="/colaboradores" method="get"class="pos-content">
                        <button type="submit" id="btn-cancelar-nuevo" class="btn btn--cancel">
                            Cancelar
                        </button>
                        </form>
                    </div>
                </aside>
            </div>
        </main>
    </div>
</div>
</body>

</html>