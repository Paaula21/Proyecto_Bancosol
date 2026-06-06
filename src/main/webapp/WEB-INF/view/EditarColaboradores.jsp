<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Colaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica" %>
<%@ page import="java.util.List" %>
<%
    Colaborador col = (Colaborador) request.getAttribute("colaboradorSeleccionado");
    ContactoColaborador con = (ContactoColaborador) request.getAttribute("contactoSeleccionado");
    List<ZonaGeografica> zonasDisponibles = (List<ZonaGeografica>) request.getAttribute("zonasDisponibles");

    String idColab = col != null ? col.getIdColaborador() : "";
    String nombreColab = col != null ? col.getNombreColaborador() : "";
    String observaciones = col != null && col.getObservaciones() != null ? col.getObservaciones() : "";

    String zona = "";
    String localidad = "";
    String tipoVia = "";
    String nombreVia = "";
    String numero = "";
    String cp = "";

    if (col != null && col.getDireccion() != null) {
        tipoVia = col.getDireccion().getTipoVia() != null ? col.getDireccion().getTipoVia() : "";
        nombreVia = col.getDireccion().getNombreVia() != null ? col.getDireccion().getNombreVia() : "";
        numero = col.getDireccion().getNumero() != null ? col.getDireccion().getNumero() : "";

        if (col.getDireccion().getCp() != null) {
            cp = col.getDireccion().getCp().getCodigo() != null ? col.getDireccion().getCp().getCodigo() : "";
            if (col.getDireccion().getCp().getDivision() != null) {
                localidad = col.getDireccion().getCp().getDivision().getNombreDivision() != null ? col.getDireccion().getCp().getDivision().getNombreDivision() : "";
                if (col.getDireccion().getCp().getDivision().getZona() != null) {
                    zona = col.getDireccion().getCp().getDivision().getZona().getNombreZona() != null ? col.getDireccion().getCp().getDivision().getZona().getNombreZona() : "";
                }
            }
        }
    }

    String cNombre = "";
    String cEmail = "";
    String cTel = "";

    if (con != null && con.getPersona() != null) {
        cNombre = con.getPersona().getNombreCompleto() != null ? con.getPersona().getNombreCompleto() : "";
        cEmail = con.getPersona().getEmail() != null ? con.getPersona().getEmail() : "";
        cTel = con.getPersona().getTelefono() != null ? con.getPersona().getTelefono() : "";
    }
%>

<div id="formulario-editar-colaborador" class="datos-colaborador" style="<%= col != null ? "display: block;" : "display: none;" %>">
    <h3 class="ficha-titulo-principal">Modificar Colaborador</h3>

    <form id="form-edicion-colaborador" action="/colaboradores/actualizar" method="POST">
        <input type="hidden" name="idColaborador" value="<%= idColab %>">

        <div class="ficha-tarjeta-info tarjeta-cabecera-edit">
            <h5>Nombre de la Entidad / Colaborador</h5>
            <input type="text" id="edit-nombre" name="nombreColaborador" value="<%= nombreColab %>" class="input-ficha-lateral input-nombre-principal" required placeholder="Ej. Cáritas Diocesana">
        </div>

        <div class="ficha-tarjeta-info">
            <h5>Ubicación</h5>
            <div class="campo-formulario-ficha">
                <label for="edit-zona"><strong>Zona Geográfica:</strong></label>
                <select id="edit-zona" name="nombreZona" class="select-ficha-lateral" required>
                    <option value="" disabled <%= zona.isEmpty() ? "selected" : "" %>>Seleccione zona...</option>
                    <%
                        if (zonasDisponibles != null) {
                            for (ZonaGeografica z : zonasDisponibles) {
                    %>
                    <option value="<%= z.getNombreZona() %>" <%= zona.equals(z.getNombreZona()) ? "selected" : "" %>>
                        <%= z.getNombreZona() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
        </div>

        <div class="ficha-tarjeta-info">
            <h5>Datos de Contacto</h5>
            <div class="campo-formulario-ficha">
                <label for="edit-contacto-nombre"><strong>Persona:</strong></label>
                <input type="text" id="edit-contacto-nombre" name="contactoNombre" value="<%= cNombre %>" class="input-ficha-lateral" placeholder="Nombre completo">
            </div>
            <div class="campo-formulario-ficha">
                <label for="edit-contacto-email"><strong>Email:</strong></label>
                <input type="email" id="edit-contacto-email" name="contactoEmail" value="<%= cEmail %>" class="input-ficha-lateral" placeholder="ejemplo@correo.com">
            </div>
            <div class="campo-formulario-ficha">
                <label for="edit-contacto-tel"><strong>Teléfono:</strong></label>
                <input type="tel" id="edit-contacto-tel" name="contactoTel" value="<%= cTel %>" class="input-ficha-lateral" placeholder="600 000 000">
            </div>
        </div>

        <div class="ficha-tarjeta-info sin-margen-inferior">
            <h5>Dirección Postal</h5>
            <div class="campo-formulario-ficha">
                <label for="edit-tipo-via"><strong>Tipo de Vía:</strong></label>
                <select id="edit-tipo-via" name="tipoVia" class="select-ficha-lateral">
                    <option value="" <%= tipoVia.isEmpty() ? "selected" : "" %>>Ninguno</option>
                    <option value="Calle" <%= tipoVia.equals("Calle") ? "selected" : "" %>>Calle</option>
                    <option value="Avenida" <%= tipoVia.equals("Avenida") ? "selected" : "" %>>Avenida</option>
                    <option value="Plaza" <%= tipoVia.equals("Plaza") ? "selected" : "" %>>Plaza</option>
                </select>
            </div>
            <div class="campo-formulario-ficha">
                <label for="edit-direccion-via"><strong>Calle / Vía:</strong></label>
                <input type="text" id="edit-direccion-via" name="nombreVia" value="<%= nombreVia %>" class="input-ficha-lateral" required placeholder="Ej. Av. de Andalucía">
            </div>
            <div class="campo-formulario-ficha">
                <label for="edit-direccion-num"><strong>Número:</strong></label>
                <input type="text" id="edit-direccion-num" name="numero" value="<%= numero %>" class="input-ficha-lateral" placeholder="Ej. 12, 3ºB">
            </div>
            <div class="campo-formulario-ficha">
                <label for="edit-cp"><strong>Cód. Postal:</strong></label>
                <input type="text" id="edit-cp" name="codigoPostal" value="<%= cp %>" class="input-ficha-lateral" required maxlength="5" placeholder="29000">
            </div>
            <div class="campo-formulario-ficha">
                <label for="edit-localidad"><strong>Localidad:</strong></label>
                <input type="text" id="edit-localidad" name="nombreDivision" value="<%= localidad %>" class="input-ficha-lateral" required placeholder="Ej. Málaga">
            </div>
        </div>

        <div class="ficha-tarjeta-info sin-margen-inferior" style="margin-top: 15px;">
            <h5>Observaciones</h5>
            <input type="text" id="edit-observaciones" name="observaciones" value="<%= observaciones %>" class="input-ficha-lateral" placeholder="Detalles adicionales...">
        </div>

    </form>
</div>

<div class="overlay" id="overlay-exito">
    <div class="popup" id="popup-exito">
        <h3>¡Guardado con éxito!</h3>
        <p>El colaborador y sus datos de contacto se han actualizado correctamente en el sistema.</p>

        <div class="popup-actions">
            <form action="">
                <button type="button" class="btn-add" id="btn-aceptar-edicion">Aceptar</button>
            </form>
        </div>
    </div>
</div>

<div class="overlay" id="overlay-error">
    <div class="popup" id="popup-error">
        <h3>Ha ocurrido un error</h3>
        <p id="texto-error-popup">No se pudieron guardar los cambios relacionales en el servidor.</p>

        <div class="popup-actions">
            <form action="">
                <button type="button" class="btn-cerrar-popup" id="btn-aceptar-error">Aceptar</button>
            </form>
        </div>
    </div>
</div>