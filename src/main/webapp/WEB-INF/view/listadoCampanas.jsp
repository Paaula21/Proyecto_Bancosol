<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Establecimiento" %>
<%
    String idCampana = (String) request.getAttribute("idCampana");
    // Recuperamos la lista de establecimientos que viene del controlador
    List<Establecimiento> listaEstablecimientos = (List<Establecimiento>) request.getAttribute("establecimientos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/AsignacionTurnos.css">
    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/Common.css">

    <title>Listado de Turnos - Campaña <%= idCampana != null ? idCampana : "" %></title>
</head>
<body>

<div class="app-container">
    <jsp:include page="sidebar.jsp" />

    <div class="right-content">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Listado de Campañas" />
            <jsp:param name="subtitulo" value="Gestion y planificacion de campañas activas" />
        </jsp:include>

        <div class="main-layout">
            <main>
                <div class="list-container" id="panel-tiendas" style="margin-bottom: 24px;">
                    <div class="list-header" style="margin-bottom: 16px;">
                        <div>
                            <h2 id="titulo-campana-tiendas">Tiendas de la Campaña <%= idCampana != null ? idCampana : "" %></h2>
                            <p style="margin: 4px 0 0 0; color: #6b7280; font-size: 0.875rem;">Mostrando todos los establecimientos de la campaña. Use los filtros para acotar los resultados.</p>
                        </div>
                    </div>

                    <%
                        // Recuperamos las variables de los filtros actuales enviados desde el controlador
                        String cadenaSeleccionada = (String) request.getAttribute("cadenaSeleccionada");
                        String idTiendaBuscado = (String) request.getAttribute("idTiendaBuscado");
                    %>

                    <form class="filters" method="GET" action="/campanas/turnos"
                          style="display: flex; flex-direction: row; align-items: flex-end; gap: 20px; background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 20px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); margin-bottom: 24px; width: 100%; box-sizing: border-box;">

                        <input type="hidden" name="id" value="<%= idCampana %>">

                        <div class="filter-group" style="display: flex; flex-direction: column; gap: 8px; flex: 2;">
                            <label for="filter-cadena" style="font-size: 0.875rem; font-weight: 600; color: #374151;">Nombre de Cadena</label>
                            <input type="text" name="cadena" id="filter-cadena" placeholder="Ej: Mercadona"
                                   value="<%= cadenaSeleccionada != null ? cadenaSeleccionada : "" %>"
                                   style="width: 100%; padding: 10px 14px; border-radius: 6px; border: 1px solid #d1d5db; font-size: 0.875rem; color: #111827; height: 42px; box-sizing: border-box; background-color: #f9fafb;">
                        </div>

                        <div class="filter-group" style="display: flex; flex-direction: column; gap: 8px; flex: 1;">
                            <label for="filter-id-tienda" style="font-size: 0.875rem; font-weight: 600; color: #374151;">Buscar por ID Tienda</label>
                            <input type="text" name="idTienda" id="filter-id-tienda" placeholder="Ej: 32"
                                   value="<%= idTiendaBuscado != null ? idTiendaBuscado : "" %>"
                                   style="width: 100%; padding: 10px 14px; border-radius: 6px; border: 1px solid #d1d5db; font-size: 0.875rem; color: #111827; height: 42px; box-sizing: border-box; background-color: #f9fafb;">
                        </div>

                        <div class="filter-button" style="margin-bottom: 0;">
                            <button type="submit" id="btn-filter" class="btn btn--primary"
                                    style="height: 42px; padding: 0 32px; font-size: 0.875rem; font-weight: 600; border-radius: 6px; display: flex; align-items: center; justify-content: center; box-sizing: border-box;">
                                Filtrar
                            </button>
                        </div>
                    </form>

                    <div class="table-wrapper">
                        <table class="data-table">
                            <thead>
                            <tr>
                                <th>Establecimiento</th>
                                <th>Cadena</th>
                                <th>ID Tienda</th>
                                <th>Acción</th>
                            </tr>
                            </thead>
                            <tbody id="tabla-tiendas-campana">
                            <%
                                if (listaEstablecimientos != null && !listaEstablecimientos.isEmpty()) {
                                    for (Establecimiento e : listaEstablecimientos) {
                            %>
                            <tr>
                                <td><strong><%= e.getNombreResena() != null ? e.getNombreResena() : "Establecimiento sin nombre" %></strong></td>

                                <td><%= (e.getCadena() != null) ? e.getCadena().getNombreCadena() : "Sin cadena asignada" %></td>

                                <td><%= e.getIdEstablecimiento() %></td>

                                <td>
                                    <button type="button" class="btn btn--primary"
                                            style="padding: 4px 10px; font-size: 0.8em;"
                                            onclick="window.location.href='/campanas/asignacion?idCampana=<%= idCampana %>&idTienda=<%= e.getIdEstablecimiento() %>'">
                                        Asignar Turnos
                                    </button>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="4" style="text-align: center; padding: 20px; color: #6b7280;">
                                    No hay ningún establecimiento asignado a esta campaña actualmente.
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>

                <section class="filters" id="filtros-voluntarios" style="display: none; margin-bottom: 24px;">
                    <div class="filter-group">
                        <label for="filter-turnos">Disponibilidad Horaria</label>
                        <select id="filter-turnos">
                            <option value=""> Todos los turnos </option>
                            <option value="lunes-mañana">Lunes mañana</option>
                            <option value="martes-mañana">Martes mañana</option>
                        </select>
                    </div>
                    <div class="filter-button">
                        <button type="button" id="btn-filter-vols" class="btn btn--primary">Filtrar</button>
                    </div>
                </section>

                <div class="list-container" id="panel-voluntarios" style="display: none;">
                    <div class="list-header">
                        <div>
                            <h2 id="titulo-voluntarios-asignar">Listado de Voluntarios</h2>
                            <p id="contador-voluntarios">Seleccione una tienda primero.</p>
                        </div>
                        <button type="button" class="btn btn--primary" onclick="window.location.href='RegistroVoluntarios.html'">Añadir Voluntario</button>
                    </div>
                    <div class="table-wrapper">
                        <table class="data-table">
                            <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Contacto</th>
                                <th>Disponibilidad</th>
                                <th>Acciones</th>
                            </tr>
                            </thead>
                            <tbody id="tabla-voluntarios">
                            <tr><td colspan="4" style="text-align: center; padding: 20px;">Seleccione una tienda arriba para ver los voluntarios.</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<div id="overlay-eliminar" class="overlay"></div>
<div id="popup-eliminar" class="popup-confirmacion">
    <h3>¿Estás seguro de que deseas eliminar este voluntario?</h3>
    <p>Esta acción eliminará de forma permanente al voluntario y a la persona vinculada en la base de datos.</p>
    <div class="popup-actions">
        <button type="button" id="btn-cancelar-eliminar" class="btn-cancelar">Cancelar</button>
        <button type="button" id="btn-confirmar-eliminar" class="btn-confirmar">Eliminar definitivamente</button>
    </div>
</div>

</body>
</html>