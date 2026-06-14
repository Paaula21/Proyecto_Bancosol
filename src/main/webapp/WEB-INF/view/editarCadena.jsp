<%--
Página JSP que muestra el formulario de editar/añadir cadena.

- Maria Muñoz Martin: 100%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%@ page import="java.util.List" %>
<%
    CadenaDTO cadena = (CadenaDTO) request.getAttribute("cadenaSeleccionada");
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    String modoPanel = (String) request.getAttribute("modoPanel");
    boolean esEditar = "editar".equals(modoPanel) && cadena != null && cadena.getIdCadena() != null;
%>
<div id="chain-form-panel">
    <h3 class="ficha-titulo-principal" id="form-title"><%= esEditar ? "Editar Cadena" : "Nueva Cadena" %></h3>
    <form id="chain-form" action="/cadenas/guardar" method="POST">
        <% if (esEditar) { %>
            <input type="hidden" name="idCadena" value="<%= cadena.getIdCadena() %>">
        <% } %>
        <div class="ficha-tarjeta-info tarjeta-cabecera-edit">
            <h5>Nombre de la Cadena</h5>
            <input type="text" id="form-name" name="nombreCadena" class="input-ficha-lateral input-nombre-principal" required placeholder="Ej. Carrefour"
                   value="<%= esEditar ? cadena.getNombreCadena() : "" %>">
        </div>
        <div class="ficha-tarjeta-info">
            <h5>Campañas</h5>
            <div class="campo-formulario-ficha">
                <label for="form-campaigns"><strong>Participación en campañas:</strong></label>
                <select id="form-campaigns" name="campanasIds" class="select-ficha-lateral" multiple size="4">
                    <% if (campanas != null) {
                        for (CampanaDTO camp : campanas) {
                            String selected = (esEditar && cadena.getCampanasIds() != null && cadena.getCampanasIds().contains(camp.getIdCampana())) ? "selected" : "";
                    %>
                        <option value="<%= camp.getIdCampana() %>" <%= selected %>><%= camp.getNombreCampana() %></option>
                    <%  }
                    } %>
                </select>
                <small class="form-hint">Mantén Ctrl (o Cmd en Mac) para seleccionar varias</small>
            </div>
        </div>
    </form>
</div>
