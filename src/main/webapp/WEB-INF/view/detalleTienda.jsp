<%--
Página JSP que muestra el panel de detalle de tienda.

- Maria Muñoz Martin: 95%
- IA generativa: 5%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO" %>
<%
    EstablecimientoDTO tienda = (EstablecimientoDTO) request.getAttribute("tiendaSeleccionada");
%>
<div id="tienda-data">
    <h3 class="ficha-titulo-principal">Ficha de la Tienda</h3>
    <div class="ficha-cabecera">
        <div>
            <h4 class="ficha-nombre"><%= tienda != null ? tienda.getNombreResena() : "---" %></h4>
        </div>
    </div>
    <div class="ficha-grid">
        <div class="ficha-tarjeta-info">
            <h5>INFORMACIÓN GENERAL</h5>
            <p><strong>Cadena:</strong> <%= tienda != null && tienda.getNombreCadena() != null ? tienda.getNombreCadena() : "---" %></p>
            <p><strong>Lineales:</strong> <%= tienda != null && tienda.getLineales() != null ? tienda.getLineales() : "---" %></p>
        </div>
        <div class="ficha-tarjeta-info">
            <h5>DIRECCIÓN</h5>
            <p><strong>Tipo Vía:</strong> <%= tienda != null && tienda.getTipoVia() != null ? tienda.getTipoVia() : "---" %></p>
            <p><strong>Nombre Vía:</strong> <%= tienda != null && tienda.getNombreVia() != null ? tienda.getNombreVia() : "---" %></p>
            <p><strong>Número:</strong> <%= tienda != null && tienda.getNumero() != null ? tienda.getNumero() : "---" %></p>
            <p><strong>Código Postal:</strong> <%= tienda != null && tienda.getCodigo() != null ? tienda.getCodigo() : "---" %></p>
            <p><strong>Localidad:</strong> <%= tienda != null && tienda.getLocalidad() != null ? tienda.getLocalidad() : "---" %></p>
            <p><strong>Zona:</strong> <%= tienda != null && tienda.getNombreZona() != null ? tienda.getNombreZona() : "---" %></p>
        </div>
        <div class="ficha-tarjeta-info">
            <h5>COORDINADOR</h5>
            <p><%= tienda != null && tienda.getCoordinadorNombre() != null ? tienda.getCoordinadorNombre() : "Sin coordinador asignado" %></p>
        </div>
    </div>
</div>
