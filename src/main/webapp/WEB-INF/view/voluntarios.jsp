<%--
Página JSP que permite muestra la pagina principal de voluntarios
- Ainhoa García Rebollo: 100%
- IA generativa: 20% -> Dudas al implementar el pop-up
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO" %>
<%
    List<VistaVoluntarioDTO> voluntarios = (List<VistaVoluntarioDTO>) request.getAttribute("voluntarios");
    String nombre = (String) request.getAttribute("nombre_completo");
    String email = (String) request.getAttribute("email");
    String telefono = (String) request.getAttribute("telefono");
    String disponibilidad = (String) request.getAttribute("disponibilidad");

    if (nombre == null) nombre = "";
    if (email == null) email = "";
    if (telefono == null) telefono = "";
    if (disponibilidad == null) disponibilidad = "";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Asignación de turnos - Voluntarios</title>
    <link rel="stylesheet" href="/css/AsignacionTurnos.css">
    <link rel="stylesheet" href="/css/Sidebar.css">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/Common.css">
</head>
<body>

<div class="app-container">

    <jsp:include page="sidebar.jsp" />

    <div class="right-content">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Voluntarios" />
            <jsp:param name="subtitulo" value="Gestión de disponibilidad y listado general" />
        </jsp:include>

        <div class="main-layout">
            <main>
                <form action="/voluntarios/filtrar" method="post">
                    <section class="filters">
                        <div class="filter-group">
                            <label for="filter-turnos">Disponibilidad Horaria</label>
                            <select id="filter-turnos" name="disponibilidad">
                                <option value="" <%= disponibilidad.isEmpty() ? "selected" : "" %>> Todos los turnos </option>
                                <option value="mañana" <%= "mañana".equals(disponibilidad) ? "selected" : "" %>>Turno de Mañana</option>
                                <option value="tarde" <%= "tarde".equals(disponibilidad) ? "selected" : "" %>>Turno de Tarde</option>
                            </select>
                        </div>
                        <div class="filter-button">
                            <button type="submit" id="btn-filter" class="btn btn--primary">Filtrar</button>
                        </div>
                    </section>
                </form>

                <div class="list-container">
                    <div class="list-header" style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 20px;">
                        <div class="header-titles">
                            <h2 style="margin: 0; margin-bottom: 5px;">Listado de Voluntarios</h2>
                            <p id="contador-voluntarios" style="margin: 0; color: #6b7280; font-size: 0.9em;">
                                <%= voluntarios != null ? voluntarios.size() : 0 %> voluntarios registrados
                            </p>
                        </div>
                        <button type="button" class="btn btn--primary"
                                onclick="window.location.href='/voluntarios/nuevo'"
                                style="padding: 10px 14px; font-size: 0.85em; height: fit-content;">
                            Añadir Voluntario
                        </button>
                    </div>

                    <div class="table-wrapper">
                        <table class="data-table">
                            <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Teléfono</th>
                                <th>Acciones</th>
                            </tr>
                            </thead>
                            <tbody id="tabla-voluntarios">
                            <%
                                if (voluntarios != null && !voluntarios.isEmpty()) {
                                    for (VistaVoluntarioDTO v : voluntarios) {
                                        String nombreV = v.getNombreCompleto() != null ? v.getNombreCompleto() : "Sin especificar";
                                        String emailV = v.getEmail() != null ? v.getEmail() : "Sin especificar";
                                        String telV = v.getTelefono() != null ? String.valueOf(v.getTelefono()) : "Sin especificar";
                            %>
                            <tr>
                                <td><strong><%= nombreV %></strong></td>
                                <td><%= emailV %></td>
                                <td><%= telV %></td>
                                <td>
                                    <a href="/voluntarios/editar?id=<%= v.getIdVoluntario() %>" class="btn btn-edit" style="text-decoration: none; border-color:#d1d5db; color: #374151;">Editar</a>
                                    <a href="/voluntarios/borrar?id=<%= v.getIdVoluntario() %>" class="btn btn--delete" style="text-decoration: none; display: inline-block; text-align: center;">Eliminar</a>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="4" style="text-align: center; padding: 30px; color: #6b7280;">No se han encontrado voluntarios que coincidan con la búsqueda.</td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>

            </main>
        </div>
    </div>
</div>

</body>
</html>