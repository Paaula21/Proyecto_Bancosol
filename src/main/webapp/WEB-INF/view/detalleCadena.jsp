<%--
Página JSP que muestra el panel de detalle de cadena.

- Maria Muñoz Martin: 95%
- IA generativa: 5%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%@ page import="java.util.List" %>
<%
    CadenaDTO cadena = (CadenaDTO) request.getAttribute("cadenaSeleccionada");
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
%>
<div id="chain-data">
    <h3 class="ficha-titulo-principal">Ficha de la Cadena</h3>
    <div class="ficha-cabecera">
        <div>
            <h4 id="record-name" class="ficha-nombre"><%= cadena != null ? cadena.getNombreCadena() : "---" %></h4>
        </div>
    </div>
    <div class="ficha-grid">
        <div class="ficha-tarjeta-info">
            <h5>INFORMACIÓN GENERAL</h5>
            <p><strong>Nº Establecimientos:</strong> <span id="record-establishments"><%= cadena != null ? cadena.getNumEstablecimientos() : "---" %></span></p>
        </div>
        <div class="ficha-tarjeta-info">
            <h5>Campañas</h5>
            <div id="record-campaigns-content">
                <% if (cadena != null && cadena.getCampanasIds() != null && !cadena.getCampanasIds().isEmpty()) {
                    for (String idCamp : cadena.getCampanasIds()) {
                        String nombreCamp = idCamp;
                        if (campanas != null) {
                            for (CampanaDTO c : campanas) {
                                if (c.getIdCampana().equals(idCamp)) {
                                    nombreCamp = c.getNombreCampana();
                                    break;
                                }
                            }
                        }
                %>
                    <p><strong><%= nombreCamp %>:</strong> Sí</p>
                <%  }
                } else { %>
                    <p>Sin campañas asociadas</p>
                <% } %>
            </div>
        </div>
    </div>
</div>
