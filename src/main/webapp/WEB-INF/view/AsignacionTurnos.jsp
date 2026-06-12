<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO" %>
<%
    // 1. Recuperamos las dos listas de voluntarios filtradas que vienen del controlador
    List<VistaVoluntarioDTO> voluntariosManana = (List<VistaVoluntarioDTO>) request.getAttribute("voluntariosManana");
    List<VistaVoluntarioDTO> voluntariosTarde = (List<VistaVoluntarioDTO>) request.getAttribute("voluntariosTarde");

    // 2. Recuperamos el Mapa que contiene las asignaciones ya guardadas en la base de datos
    Map<String, String> asignaciones = (Map<String, String>) request.getAttribute("asignaciones");
    System.out.println(asignaciones);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Planificación de Turnos</title>

    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/Common.css">
</head>
<body>

<div class="app-container">

    <jsp:include page="sidebar.jsp" />

    <div class="right-content">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Listado de Campañas" />
            <jsp:param name="subtitulo" value="Gestión y planificación de campañas activas" />
        </jsp:include>

        <div class="main-layout">
            <main style="padding: 24px; box-sizing: border-box; width: 85%; margin: 20px auto 24px auto; background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 8px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05); overflow-y: auto;">

                <form action="/campanas/turnos/guardar" method="POST">

                    <input type="hidden" name="idCampana" value="${idCampana}">
                    <input type="hidden" name="idTienda" value="${idTienda}">

                    <div class="list-header" style="margin-bottom: 24px; padding-bottom: 16px; border-bottom: 1px solid #e5e7eb;">
                        <h2 style="margin: 0; color: #111827;">Planificación de Turnos</h2>
                        <p style="margin: 8px 0 0 0; color: #4b5563; font-size: 0.9rem;">
                            Campaña: <strong style="color: #111827;">${idCampana}</strong> | ID Tienda: <strong style="color: #111827;">${idTienda}</strong>
                        </p>
                    </div>

                    <div class="table-wrapper">
                        <table class="data-table" style="width: 100%; border-collapse: collapse; text-align: left;">
                            <thead>
                            <tr>
                                <th style="padding: 12px; background-color: #f9fafb; border-bottom: 2px solid #e5e7eb; color: #374151;">Día</th>
                                <th style="padding: 12px; background-color: #f9fafb; border-bottom: 2px solid #e5e7eb; color: #374151;">Turno Mañana</th>
                                <th style="padding: 12px; background-color: #f9fafb; border-bottom: 2px solid #e5e7eb; color: #374151;">Turno Tarde</th>
                            </tr>
                            </thead>
                            <tbody>

                            <%
                                String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
                                String[] idsDia     = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};

                                for (int i = 0; i < diasSemana.length; i++) {
                                    String dia   = diasSemana[i];
                                    String idDia = idsDia[i];  // ← clave limpia sin tildes

                                    String nombreSelectManana = "asignacion_manana_" + idDia;
                                    String nombreSelectTarde  = "asignacion_tarde_"  + idDia;
                            %>
                            <tr style="border-bottom: 1px solid #e5e7eb;">
                                <td style="padding: 12px; font-weight: 600; color: #111827;"><%= dia %></td>

                                <td style="padding: 12px;">
                                    <select name="<%= nombreSelectManana %>" style="width: 100%; padding: 8px; border-radius: 6px; border: 1px solid #d1d5db; background-color: #f9fafb;">
                                        <option value="">Sin asignar</option>
                                        <%
                                            if (voluntariosManana != null) {
                                                for (VistaVoluntarioDTO v : voluntariosManana) {
                                                    // Verificamos si este voluntario en concreto ya está asignado en la BD
                                                    boolean mSeleccionado = asignaciones != null && v.getIdVoluntario().equals(asignaciones.get(nombreSelectManana));
                                        %>
                                        <option value="<%= v.getIdVoluntario() %>" <%= mSeleccionado ? "selected" : "" %>>
                                            <%= v.getNombreCompleto() %>
                                        </option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </td>

                                <td style="padding: 12px;">
                                    <select name="<%= nombreSelectTarde %>" style="width: 100%; padding: 8px; border-radius: 6px; border: 1px solid #d1d5db; background-color: #f9fafb;">
                                        <option value="">Sin asignar</option>
                                        <%
                                            if (voluntariosTarde != null) {
                                                for (VistaVoluntarioDTO v : voluntariosTarde) {
                                                    // Verificamos si este voluntario en concreto ya está asignado en la BD
                                                    boolean tSeleccionado = asignaciones != null && v.getIdVoluntario().equals(asignaciones.get(nombreSelectTarde));
                                        %>
                                        <option value="<%= v.getIdVoluntario() %>" <%= tSeleccionado ? "selected" : "" %>>
                                            <%= v.getNombreCompleto() %>
                                        </option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <% } %>

                            </tbody>
                        </table>
                    </div>

                    <div class="popup-actions" style="display: flex; flex-direction: row; justify-content: flex-end; gap: 12px; margin-top: 30px;">
                        <button type="button" class="btn btn--cancel" onclick="window.history.back()">Cancelar</button>
                        <button type="submit" class="btn btn--primary">Guardar Cambios</button>
                    </div>

                </form>

            </main>
        </div>
    </div>
</div>

</body>
</html>