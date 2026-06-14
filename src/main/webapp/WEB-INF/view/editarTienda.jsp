<%--
Página JSP que muestra el formulario de editar/añadir tienda.

- Maria Muñoz Martin: 100%
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica" %>
<%@ page import="java.util.List" %>
<%
    EstablecimientoDTO tienda = (EstablecimientoDTO) request.getAttribute("tiendaSeleccionada");
    List<CadenaDTO> todasCadenas = (List<CadenaDTO>) request.getAttribute("todasCadenas");
    List<ZonaGeografica> zonas = (List<ZonaGeografica>) request.getAttribute("zonas");
    String modoPanel = (String) request.getAttribute("modoPanel");
    boolean esEditar = "editar".equals(modoPanel) && tienda != null && tienda.getIdEstablecimiento() != null;

    String[] tiposVia = {"Calle", "Avenida", "Plaza"};
%>
<div id="tienda-form-panel">
    <h3 class="ficha-titulo-principal"><%= esEditar ? "Editar Tienda" : "Nueva Tienda" %></h3>
    <form id="tienda-form" action="/tiendas/guardar" method="POST">
        <% if (esEditar) { %>
            <input type="hidden" name="idEstablecimiento" value="<%= tienda.getIdEstablecimiento() %>">
        <% } %>
        <div class="ficha-tarjeta-info tarjeta-cabecera-edit">
            <h5>Cadena</h5>
            <select name="idCadena" class="select-ficha-lateral" required>
                <option value="">Seleccione una cadena</option>
                <% if (todasCadenas != null) {
                    for (CadenaDTO c : todasCadenas) {
                        String selected = (esEditar && tienda.getIdCadena() != null && tienda.getIdCadena().equals(c.getIdCadena())) ? "selected" : "";
                %>
                    <option value="<%= c.getIdCadena() %>" <%= selected %>><%= c.getNombreCadena() %></option>
                <%  }
                } %>
            </select>
        </div>
        <div class="ficha-tarjeta-info tarjeta-cabecera-edit">
            <h5>Nombre de la Tienda</h5>
            <input type="text" name="nombreResena" class="input-ficha-lateral input-nombre-principal" required placeholder="Ej. Tienda Centro"
                   value="<%= esEditar ? tienda.getNombreResena() : "" %>">
        </div>
        <div class="ficha-tarjeta-info">
            <h5>Lineales</h5>
            <input type="number" name="lineales" class="input-ficha-lateral" placeholder="Nº de lineales"
                   value="<%= esEditar && tienda.getLineales() != null ? tienda.getLineales() : "" %>">
        </div>
        <div class="ficha-tarjeta-info">
            <h5>DIRECCIÓN</h5>
            <div class="campo-formulario-ficha">
                <label for="form-tipo-via"><strong>Tipo de Vía:</strong></label>
                <select id="form-tipo-via" name="tipoVia" class="select-ficha-lateral" required>
                    <% for (String tv : tiposVia) {
                        String selected = (esEditar && tienda.getTipoVia() != null && tienda.getTipoVia().equals(tv)) ? "selected" : "";
                    %>
                        <option value="<%= tv %>" <%= selected %>><%= tv %></option>
                    <% } %>
                </select>
            </div>
            <div class="campo-formulario-ficha">
                <label for="form-nombre-via"><strong>Nombre de la Vía:</strong></label>
                <input type="text" id="form-nombre-via" name="nombreVia" class="input-ficha-lateral" required placeholder="Ej. Gran Vía"
                       value="<%= esEditar ? tienda.getNombreVia() : "" %>">
            </div>
            <div class="campo-formulario-ficha">
                <label for="form-numero"><strong>Número:</strong></label>
                <input type="text" id="form-numero" name="numero" class="input-ficha-lateral" placeholder="Ej. 42"
                       value="<%= esEditar && tienda.getNumero() != null ? tienda.getNumero() : "" %>">
            </div>
            <div class="campo-formulario-ficha">
                <label for="form-codigo"><strong>Código Postal:</strong></label>
                <input type="text" id="form-codigo" name="codigo" class="input-ficha-lateral" required placeholder="Ej. 29001"
                       value="<%= esEditar && tienda.getCodigo() != null ? tienda.getCodigo() : "" %>">
            </div>
            <div class="campo-formulario-ficha">
                <label for="form-localidad"><strong>Localidad:</strong></label>
                <input type="text" id="form-localidad" name="localidad" class="input-ficha-lateral" required placeholder="Ej. Málaga"
                       value="<%= esEditar && tienda.getLocalidad() != null ? tienda.getLocalidad() : "" %>">
            </div>
            <div class="campo-formulario-ficha">
                <label for="form-zona"><strong>Zona:</strong></label>
                <select id="form-zona" name="idZona" class="select-ficha-lateral" required>
                    <option value="">Seleccione una zona</option>
                    <% if (zonas != null) {
                        for (ZonaGeografica z : zonas) {
                            String selected = (esEditar && tienda.getIdZona() != null && tienda.getIdZona().equals(z.getIdZona())) ? "selected" : "";
                    %>
                        <option value="<%= z.getIdZona() %>" <%= selected %>><%= z.getNombreZona() %></option>
                    <%  }
                    } %>
                </select>
            </div>
        </div>
    </form>
</div>
