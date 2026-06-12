<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Cadena" %>
<%
    String idCampana = (String) request.getAttribute("idCampana");
    // Recuperamos la lista como lo que realmente es: una lista de Cadenas
    List<Cadena> listaCadenas = (List<Cadena>) request.getAttribute("tiendas");
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
    <div class="right-content">
        <div class="main-layout">
            <main>
                <div class="list-container" id="panel-tiendas" style="margin-bottom: 24px;">
                    <div class="list-header" style="margin-bottom: 16px;">
                        <div>
                            <h2 id="titulo-campana-tiendas">Tiendas de la Campaña <%= idCampana != null ? idCampana : "" %></h2>
                            <p style="margin: 4px 0 0 0; color: #6b7280; font-size: 0.875rem;">Mostrando todos los establecimientos de la campaña. Use los filtros para acotar los resultados.</p>
                        </div>
                    </div>

                    <section class="filters" style="display: flex; align-items: flex-end; gap: 16px; background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 8px; padding: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); margin-bottom: 24px; width: 100%;">
                        <div class="filter-group" style="display: flex; flex-direction: column; gap: 6px; flex: 1;">
                            <label for="filter-cadena" style="font-size: 0.875rem; font-weight: 700; color: #111827;">Cadena</label>
                            <select id="filter-cadena" style="width: 100%; padding: 10px 12px; border-radius: 6px; border: 1px solid #d1d5db; background-color: #fff; font-size: 0.875rem; color: #374151; height: 42px;">
                                <option value="">Todas las cadenas</option>
                            </select>
                        </div>

                        <div class="filter-group" style="display: flex; flex-direction: column; gap: 6px; flex: 1;">
                            <label for="filter-id-tienda" style="font-size: 0.875rem; font-weight: 700; color: #111827;">Buscar por ID Tienda</label>
                            <input type="text" id="filter-id-tienda" placeholder="Ej: 32" style="width: 100%; padding: 10px 12px; border-radius: 6px; border: 1px solid #d1d5db; font-size: 0.875rem; color: #374151; height: 42px;">
                        </div>

                        <div class="filter-button" style="margin-bottom: 0;">
                            <button type="button" id="btn-filter" class="btn btn--primary" style="height: 42px; padding: 0 24px; font-size: 0.875rem; font-weight: 600;">Filtrar</button>
                        </div>
                    </section>

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
                                if (listaCadenas != null && !listaCadenas.isEmpty()) {
                                    for (Cadena c : listaCadenas) {
                            %>
                            <tr>
                                <td><strong><%= c.getNombreCadena() != null ? c.getNombreCadena() : "Sin nombre" %></strong></td>

                                <td><%= c.getNombreCadena() != null ? c.getNombreCadena() : "Sin cadena" %></td>

                                <td><%= c.getIdCadena() %></td>

                                <td>
                                    <button type="button" class="btn btn--primary"
                                            style="padding: 4px 10px; font-size: 0.8em;"
                                            onclick="mostrarVoluntarios('<%= c.getIdCadena() %>')">
                                        Ver Voluntarios
                                    </button>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="4" style="text-align: center; padding: 20px; color: #6b7280;">
                                    No hay ninguna cadena asignada a esta campaña actualmente.
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