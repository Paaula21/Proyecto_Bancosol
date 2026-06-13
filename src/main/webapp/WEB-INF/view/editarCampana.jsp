<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="java.util.List" %>
<%
    CampanaDTO campana = (CampanaDTO) request.getAttribute("campanaSeleccionada");
    List<CadenaDTO> todasCadenas = (List<CadenaDTO>) request.getAttribute("todasCadenas");
    String modoPanel = (String) request.getAttribute("modoPanel");

    boolean isEdit = "editar".equals(modoPanel);

    String idCampana = isEdit && campana != null ? campana.getIdCampana() : "";
    String nombre = isEdit && campana != null ? campana.getNombreCampana() : "";
    String inicio = isEdit && campana != null && campana.getFechaInicio() != null ? campana.getFechaInicio().toString() : "";
    String fin = isEdit && campana != null && campana.getFechaFin() != null ? campana.getFechaFin().toString() : "";
    String estado = isEdit && campana != null ? campana.getEstado() : "Planificada";
%>

<div class="datos-colaborador">
    <h3 class="ficha-titulo-principal"><%= isEdit ? "Modificar Campaña" : "Nueva Campaña" %></h3>

    <form id="form-campana" action="/campanas/guardar" method="POST">
        <input type="hidden" name="idCampana" value="<%= idCampana %>">

        <div class="ficha-tarjeta-info tarjeta-cabecera-edit">
            <h5>Nombre de la Campaña</h5>
            <input type="text" name="nombreCampana" value="<%= nombre %>" class="input-ficha-lateral input-nombre-principal" required placeholder="Ej. Gran Recogida 2024">
        </div>

        <div class="ficha-tarjeta-info">
            <h5>Estado Actual</h5>
            <select name="estado" class="select-ficha-lateral" required>
                <option value="Planificada" <%= "Planificada".equals(estado) ? "selected" : "" %>>Planificada</option>
                <option value="Activa" <%= "Activa".equals(estado) ? "selected" : "" %>>Activa</option>
                <option value="Finalizada" <%= "Finalizada".equals(estado) ? "selected" : "" %>>Finalizada</option>
                <option value="Cancelada" <%= "Cancelada".equals(estado) ? "selected" : "" %>>Cancelada</option>
            </select>
        </div>

        <div class="ficha-tarjeta-info">
            <h5>Fechas</h5>
            <div class="campo-formulario-ficha">
                <label><strong>Fecha Inicio:</strong></label>
                <input type="date" name="fechaInicio" value="<%= inicio %>" class="input-ficha-lateral" required>
            </div>
            <div class="campo-formulario-ficha" style="margin-top: 10px;">
                <label><strong>Fecha Fin:</strong></label>
                <input type="date" name="fechaFin" value="<%= fin %>" class="input-ficha-lateral" required>
            </div>
        </div>

        <div class="ficha-tarjeta-info sin-margen-inferior">
            <h5>Cadenas Participantes</h5>
            <div class="checkbox-list" style="max-height: 200px; overflow-y: auto; border: 1px solid #cbd5e0; padding: 10px; border-radius: 6px; background-color: #ffffff;">
                <% if (todasCadenas != null && !todasCadenas.isEmpty()) {
                    for (CadenaDTO cadena : todasCadenas) {
                        boolean checked = isEdit && campana != null && campana.getIdsCadenas() != null && campana.getIdsCadenas().contains(cadena.getIdCadena());
                %>
                <label style="display: flex; align-items: center; gap: 8px; margin-bottom: 8px; cursor: pointer;">
                    <input type="checkbox" name="cadenasIds" value="<%= cadena.getIdCadena() %>" <%= checked ? "checked" : "" %> style="width: auto;">
                    <%= cadena.getNombreCadena() %>
                </label>
                <%  }
                } else { %>
                <p style="margin: 0; color: #6b7280; font-size: 0.9rem;">No hay cadenas registradas en el sistema.</p>
                <% } %>
            </div>
        </div>
    </form>
</div>