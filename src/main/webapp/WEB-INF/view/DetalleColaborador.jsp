<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Colaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador" %>
<%
    // Recuperamos los atributos inyectados por el controlador
    Colaborador colab = (Colaborador) request.getAttribute("colaboradorSeleccionado");
    ContactoColaborador contactoDetalle = (ContactoColaborador) request.getAttribute("contactoSeleccionado");

    // Preparamos las variables con valores por defecto para evitar NullPointerExceptions
    String nombreColab = colab != null ? colab.getNombreColaborador() : "---";
    String codigoColab = colab != null ? colab.getIdColaborador() : "---";

    String zonaDetalle = "Sin asignar";
    String localidadDetalle = "Sin asignar";
    String direccionDetalle = "---";

    if (colab != null && colab.getDireccion() != null) {
        String tipoVia = colab.getDireccion().getTipoVia() != null ? colab.getDireccion().getTipoVia() + " " : "";
        String nombreVia = colab.getDireccion().getNombreVia() != null ? colab.getDireccion().getNombreVia() : "";
        String numero = colab.getDireccion().getNumero() != null ? colab.getDireccion().getNumero() : "S/N";
        String cp = colab.getDireccion().getCp() != null ? colab.getDireccion().getCp().getCodigo() : "";

        direccionDetalle = tipoVia + nombreVia + ", Nº " + numero + " - " + cp;

        if (colab.getDireccion().getCp() != null && colab.getDireccion().getCp().getDivision() != null) {
            localidadDetalle = colab.getDireccion().getCp().getDivision().getNombreDivision();
            if (colab.getDireccion().getCp().getDivision().getZona() != null) {
                zonaDetalle = colab.getDireccion().getCp().getDivision().getZona().getNombreZona();
            }
        }
    }

    String personaNombre = "---";
    String personaEmail = "---";
    String personaTel = null; // Se deja null para evaluar la condición más abajo

    if (contactoDetalle != null && contactoDetalle.getPersona() != null) {
        personaNombre = contactoDetalle.getPersona().getNombreCompleto() != null ? contactoDetalle.getPersona().getNombreCompleto() : "---";
        personaEmail = contactoDetalle.getPersona().getEmail() != null ? contactoDetalle.getPersona().getEmail() : "---";
        personaTel = contactoDetalle.getPersona().getTelefono();
    }
%>

<div id="estado-vacio" class="estado-vacio" style="<%= colab == null ? "display: block;" : "display: none;" %>">
    <h3>Detalle de Colaborador</h3>
    <p>Haga clic en un colaborador de la lista para ver sus detalles.</p>
</div>

<div id="datos-colaborador" class="datos-colaborador" style="<%= colab != null ? "display: block;" : "display: none;" %>">
    <h3 class="ficha-titulo-principal">Ficha del Colaborador</h3>

    <div class="ficha-cabecera">
        <div class="ficha-titulos">
            <h4 id="ficha-nombre" class="ficha-nombre"><%= nombreColab %></h4>
            <span id="ficha-codigo" class="ficha-etiqueta-codigo"><%= codigoColab %></span>
        </div>
    </div>

    <div class="ficha-tarjeta-info">
        <h5>Ubicación</h5>
        <p><strong>Zona:</strong> <span id="ficha-zona"><%= zonaDetalle %></span></p>
    </div>

    <div class="ficha-tarjeta-info">
        <h5>Contacto</h5>
        <p><strong>Persona:</strong> <span id="ficha-contacto-nombre"><%= personaNombre %></span></p>

        <p id="contenedor-email">
            <strong>Email:</strong>
            <a href="mailto:<%= personaEmail %>" id="ficha-contacto-email" class="ficha-enlace-tel"><%= personaEmail %></a>
        </p>

        <% if (personaTel != null && !personaTel.trim().isEmpty()) { %>
        <p id="contenedor-tel">
            <strong>Teléfono:</strong>
            <a href="tel:<%= personaTel %>" id="ficha-contacto-tel" class="ficha-enlace-tel"><%= personaTel %></a>
        </p>
        <% } %>
    </div>

    <div class="ficha-tarjeta-info sin-margen-inferior">
        <h5>Dirección Postal</h5>
        <p id="ficha-direccion"><%= direccionDetalle %></p>
        <p id="ficha-localidad" class="sin-margen"><%= localidadDetalle %></p>
    </div>
</div>