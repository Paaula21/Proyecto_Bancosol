<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    List<CadenaDTO> todasCadenas = (List<CadenaDTO>) request.getAttribute("todasCadenas");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Incidencias</title>
    <link rel="stylesheet" href="../css/Common.css">
    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/EditarAnadirColaborador.css">
    <link rel="stylesheet" href="../css/InformacionIncidencia.css">
</head>
<body>
<div class="app-container">
    <jsp:include page="sidebar.jsp" />
    <div class="right-content">
        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Incidencias" />
            <jsp:param name="subtitulo" value="Registro de incidencias" />
        </jsp:include>

        <div class="main-layout">
            <main>
                <div class="form-page-card">
                    <div class="form-card-scroll">
                        <div class="datos-colaborador">
                        <h3 class="ficha-titulo-principal">Registro de Incidencias</h3>
                        <form id="form-incidencias" action="/incidencias/guardar" method="POST">
                            <div class="ficha-tarjeta-info">
                                <h5>Rol del usuario <span class="asterisco-rojo">*</span></h5>
                                <div class="campo-formulario-ficha">
                                    <select name="rol" class="select-ficha-lateral" required>
                                        <option value="Coordinador">Coordinador</option>
                                        <option value="Voluntario">Voluntario</option>
                                        <option value="Responsable de tienda">Responsable de tienda</option>
                                        <option value="Otro">Otro</option>
                                    </select>
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Nombre de la persona <span class="asterisco-rojo">*</span></h5>
                                <div class="campo-formulario-ficha">
                                    <input type="text" name="nombre_persona" class="input-ficha-lateral"
                                           required minlength="2" placeholder="Nombre y apellidos" />
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Campaña <span class="asterisco-rojo">*</span></h5>
                                <div class="campo-formulario-ficha">
                                    <select name="id_campana" class="select-ficha-lateral" required>
                                        <option value="">Seleccionar campaña</option>
                                        <% if (campanas != null) {
                                            for (CampanaDTO c : campanas) { %>
                                                <option value="<%= c.getIdCampana() %>"><%= c.getNombreCampana() %></option>
                                        <%  }
                                        } %>
                                    </select>
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Cadena</h5>
                                <div class="campo-formulario-ficha">
                                    <select name="id_cadena" class="select-ficha-lateral">
                                        <option value="">Seleccionar cadena</option>
                                        <% if (todasCadenas != null) {
                                            for (CadenaDTO c : todasCadenas) { %>
                                                <option value="<%= c.getIdCadena() %>"><%= c.getNombreCadena() %></option>
                                        <%  }
                                        } %>
                                    </select>
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Tienda</h5>
                                <div class="campo-formulario-ficha">
                                    <input type="text" name="tienda" class="input-ficha-lateral"
                                           placeholder="Nombre o ubicación de la tienda" />
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Turno</h5>
                                <div class="turno-row">
                                    <div class="campo-formulario-ficha turno-column">
                                        <label for="turno-dia"><strong>Día:</strong></label>
                                        <select id="turno-dia" name="turno_dia" class="select-ficha-lateral">
                                            <option value="Lunes">Lunes</option>
                                            <option value="Martes">Martes</option>
                                            <option value="Miércoles">Miércoles</option>
                                            <option value="Jueves">Jueves</option>
                                            <option value="Viernes">Viernes</option>
                                            <option value="Sábado">Sábado</option>
                                            <option value="Domingo">Domingo</option>
                                        </select>
                                    </div>
                                    <div class="campo-formulario-ficha turno-column">
                                        <label for="turno-franja"><strong>Franja:</strong></label>
                                        <select id="turno-franja" name="turno_franja" class="select-ficha-lateral">
                                            <option value="Mañana">Mañana</option>
                                            <option value="Tarde">Tarde</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Nivel de urgencia <span class="asterisco-rojo">*</span></h5>
                                <div class="campo-formulario-ficha">
                                    <select name="urgencia" class="select-ficha-lateral" required>
                                        <option value="Baja">Baja</option>
                                        <option value="Media" selected>Media</option>
                                        <option value="Alta">Alta</option>
                                    </select>
                                </div>
                            </div>

                            <div class="ficha-tarjeta-info">
                                <h5>Descripción <span class="asterisco-rojo">*</span></h5>
                                <div class="campo-formulario-ficha">
                                    <textarea name="descripcion" class="input-ficha-lateral" required
                                              rows="4" placeholder="Describe la incidencia..."></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    </div>
                    <div class="detail-actions-sticky">
                        <button type="submit" form="form-incidencias" class="btn btn--primary">Registrar incidencia</button>
                        <a href="/dashboard" class="btn btn--cancel">Cancelar</a>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>
</body>
</html>
