<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Campana" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Recuperamos la lista de campañas enviada por el CampanaController
    List<Campana> listaCampanas = (List<Campana>) request.getAttribute("campanas");
%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/Campana.css">
    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/popUpRegistro.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/DetalleColaborador.css">
    <link rel="stylesheet" href="../css/EditarAnadirColaborador.css">
    <link rel="stylesheet" href="../css/EditarCampana.css">
    <link rel="stylesheet" href="../css/Common.css">

    <title>Vista de campañas</title>
</head>

<body>
<div class="main-container">
    <div class="right-container">
        <main class="campana-container">
            <section class="filters">
                <div class="filter-group" id="estado">
                    <label>Estado</label>
                    <select id="filter-state">
                        <option value="Todos">Todos</option>
                        <option value="Planificada">Planificada</option>
                        <option value="Activa">Activa</option>
                        <option value="Finalizada">Finalizada</option>
                        <option value="Cancelada">Cancelada</option>
                    </select>
                </div>

                <div class="filter-group" id="search">
                    <label>Buscar</label>
                    <input type="text" id="filter-search" placeholder="Nombre de campaña...">
                </div>

                <div class="filter-button">
                    <button type="button" id="btn-filter">Filtrar</button>
                </div>
            </section>

            <div class="content-wrapper">
                <section class="list-container">

                    <header class="list-header" style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 20px;">
                        <div class="header-titles">
                            <h2 style="margin: 0; margin-bottom: 5px;">Listado de Campañas</h2>
                            <p id="total-campanas" style="margin: 0; color: #6b7280; font-size: 0.9em;">
                                <%= listaCampanas != null ? listaCampanas.size() : 0 %> campañas registradas
                            </p>
                        </div>

                        <div style="display: flex; gap: 10px; align-items: center;">
                            <button type="button" class="btn-history" onclick="window.location.href='/historial'">
                                Ver historial
                            </button>
                            <button type="button" class="btn btn--primary"
                                    onclick="window.location.href='/campanas/nueva'"
                                    style="padding: 6px 14px; font-size: 0.85em; height: fit-content;">
                                Añadir campaña
                            </button>
                        </div>
                    </header>

                    <table>
                        <thead>
                        <tr>
                            <th>Campaña</th>
                            <th>Fecha de Inicio</th>
                            <th>Fecha de Fin</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody id="table-campanas">
                        <%
                            if (listaCampanas != null && !listaCampanas.isEmpty()) {
                                for (Campana c : listaCampanas) {
                        %>
                        <tr>
                            <td><strong><%= c.getNombreCampana() %></strong></td>
                            <td><%= c.getFechaInicio() != null ? c.getFechaInicio() : "-" %></td>
                            <td><%= c.getFechaFin() != null ? c.getFechaFin() : "-" %></td>
                            <td>
            <span class="badge <%= "Activa".equalsIgnoreCase(c.getEstado()) ? "badge--success" : "badge--secondary" %>">
                <%= c.getEstado() != null ? c.getEstado() : "Sin estado" %>
            </span>
                            </td>
                            <td>
                                <div style="display: flex; gap: 5px; align-items: center;">
                                    <button type="button" class="btn-action turnos"
                                            onclick="window.location.href='/campanas/turnos?id=<%= c.getIdCampana() %>'"
                                            style="padding: 2px 8px; font-size: 0.8em; background-color: #3b82f6; color: white; border: none; border-radius: 4px; cursor: pointer;">
                                        Ver turnos
                                    </button>

                                    <button class="btn-action edit" onclick="window.location.href='/campanas/editar?id=<%= c.getIdCampana() %>'">✏️</button>
                                    <button class="btn-action delete" onclick="document.getElementById('overlay-delete').style.display='flex'">🗑️</button>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5" style="text-align: center; color: #9ca3af; padding: 20px;">
                                No hay ninguna campaña registrada actualmente.
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </section>

                <aside class="detail-panel" id="detail-panel">
                    <div class="detail-content">
                        <include-html src="../html/DetalleCampana.html"></include-html>
                        <include-html src="../html/EditarCampana.html"></include-html>
                    </div>
                    <div class="detail-actions-sticky" id="detail-actions-campaign" style="display: none;">
                        <button type="button" id="btn-edit-campaign" class="btn-ficha btn-editar">
                            Editar Campaña
                        </button>
                        <button type="button" id="btn-delete-campaign" class="btn-ficha btn-eliminar">
                            Eliminar
                        </button>
                    </div>
                    <div class="detail-actions-sticky" id="edit-actions-campaign" style="display: none;">
                        <button type="button" id="btn-save-changes-campaign" class="btn-ficha btn-guardar">
                            Guardar Cambios
                        </button>
                        <button type="button" id="btn-cancel-edit-campaign" class="btn-ficha btn-cancel-edit">
                            Cancelar
                        </button>
                    </div>
                </aside>
            </div>
        </main>
    </div>
</div>

<div class="overlay" id="overlay-delete" style="display:none;">
    <div class="popup" id="popup-delete">
        <h3>¿Eliminar Campaña?</h3>
        <p>Esta acción no se puede deshacer. ¿Estás seguro de que deseas eliminar esta campaña de la base de datos?</p>
        <div>
            <form action="" onsubmit="return false;">
                <button class="btn btn--delete" id="btn-confirm-delete">Eliminar</button>
                <button class="btn btn--cancel" id="btn-cancel-delete" onclick="document.getElementById('overlay-delete').style.display='none'">Cancelar</button>
            </form>
        </div>
    </div>
</div>

<div class="overlay" id="overlay-success-campaign" style="display:none;">
    <div class="popup" id="popup-success-campaign">
        <h3>¡Guardado con éxito!</h3>
        <p>La campaña y sus cadenas asociadas se han actualizado correctamente.</p>
        <div>
            <form action="" onsubmit="return false;">
                <button type="button" class="btn btn--primary" id="btn-accept-edit-campaign" onclick="document.getElementById('overlay-success-campaign').style.display='none'">Aceptar</button>
            </form>
        </div>
    </div>
</div>

<div class="overlay" id="overlay-error-campaign" style="display:none;">
    <div class="popup" id="popup-error-campaign">
        <h3>Ha ocurrido un error</h3>
        <p id="error-text-popup-campaign">No se han podido guardar los cambios en el servidor.</p>
        <div>
            <form action="" onsubmit="return false;">
                <button type="button" class="btn btn--primary" id="btn-accept-error-campaign" onclick="document.getElementById('overlay-error-campaign').style.display='none'">Aceptar</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>