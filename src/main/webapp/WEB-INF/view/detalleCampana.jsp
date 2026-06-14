<%--
Página JSP que muestra el panel de los detalles de las campañas
Autores:
- Andrea Pérez Rodríguez: 100%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%
    CampanaDTO campana = (CampanaDTO) request.getAttribute("campanaSeleccionada");
%>

<div class="datos-colaborador">
    <h3 class="ficha-titulo-principal">Detalle de la Campaña</h3>

    <div class="ficha-cabecera">
        <div class="ficha-titulos">
            <h4 class="ficha-nombre"><%= campana.getNombreCampana() %></h4>
            <span class="ficha-etiqueta-codigo" style="background-color: #6b7280;"><%= campana.getEstado() %></span>
        </div>
    </div>

    <div class="ficha-tarjeta-info">
        <h5>Fechas de Actividad</h5>
        <p><strong>Inicio:</strong> <%= campana.getFechaInicio() != null ? campana.getFechaInicio() : "Sin fecha" %></p>
        <p><strong>Fin:</strong> <%= campana.getFechaFin() != null ? campana.getFechaFin() : "Sin fecha" %></p>
    </div>

    <div class="ficha-tarjeta-info sin-margen-inferior">
        <h5>Cadenas Asociadas</h5>
        <% if (campana.getIdsCadenas() != null && !campana.getIdsCadenas().isEmpty()) { %>
        <ul class="lista-cadenas-detalle">
            <% for (String idCadena : campana.getIdsCadenas()) { %>
            <li><%= idCadena %></li>
            <% } %>
        </ul>
        <% } else { %>
        <p>No hay cadenas asignadas a esta campaña.</p>
        <% } %>
    </div>
</div>