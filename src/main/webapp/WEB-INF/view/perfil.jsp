<!--
Página JSP que muestra el perfil de usuario para cambiar la contraseña y exportar los datos

Autores:
- Paula Fernández Jiménez: 100%
-->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO" %>
<%
    UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("user");
    String mensajeTexto = (String) request.getAttribute("mensajeTexto");
    String mensajeTipo = (String) request.getAttribute("mensajeTipo");

    //Como recibimos el id del rol, dependiendo del valor, lo asociamos a su valor correpsondiente
    java.util.Map<String, String> rolesTexto = java.util.Map.of(
            "1", "Administrador",
            "2", "Coordinador",
            "3", "Colaborador"
    );
    String rolTexto = rolesTexto.getOrDefault(String.valueOf(usuarioLogueado.getIdRol()), "Invitado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil de Usuario - BancoSol</title>
    <link rel="stylesheet" href="/css/Common.css">
    <link rel="stylesheet" href="/css/Sidebar.css">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/PerfilUsuario.css">
</head>
<body>
<div class="main-layout">
        <jsp:include page="sidebar.jsp" />
    <div class="right-content">
        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Perfil de usuario" />
            <jsp:param name="subtitulo" value="Ajustes del usuario" />
        </jsp:include>
        <main class="content-wrapper">
            <div class="content">
                <h3 class="title">Información del Perfil</h3>

                <div class="data-perfil">
                    <div class="data-item">
                        <label class="label-name">Nombre</label>
                        <input type="text" readonly value="<%= usuarioLogueado.getUsuario() %>" class="input" />
                    </div>
                    <div class="data-item">
                        <label class="label-name">Rol en el sistema</label>
                        <input type="text" readonly value="<%= rolTexto %>" class="input" />
                    </div>
                </div>
            </div>

            <div class="section-union">
                <div class="content content-double">
                    <h3 class="title">Contraseña</h3>

                    <form action="/perfil/cambiar-contrasena" method="POST" class="form">
                        <div class="table-data">
                            <div class="data-item">
                                <label class="label-name">Contraseña actual</label>
                                <input class="input" type="password" name="actual" required />
                            </div>
                            <div class="data-item">
                                <label class="label-name">Nueva contraseña</label>
                                <input class="input" type="password" name="nueva" required />
                            </div>
                            <div class="data-item">
                                <label class="label-name">Confirmar nueva contraseña</label>
                                <input class="input" type="password" name="confirmacion" required />
                            </div>
                        </div>
                        <div>
                            <% if (mensajeTexto != null) { %>
                            <div class='text-mensaje' style='color: <%= "error".equals(mensajeTipo) ? "red" : "green" %>'>
                                <%= mensajeTexto %>
                            </div>
                            <% } %>
                            <button type='submit' class='btn btn--primary'>Guardar Cambios</button>
                        </div>
                    </form>
                </div>

                <% if ("1".equals(String.valueOf(usuarioLogueado.getIdRol())) || "2".equals(String.valueOf(usuarioLogueado.getIdRol()))) { %>
                <div class="content content-double">
                    <h3 class="title">Exportar Datos del Sistema</h3>

                    <form action="/perfil/exportar" method="POST">
                        <div class="data-item">
                            <label class="label-name">Tablas a exportar:</label>
                            <div class="selection-content">
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="campana" class="selection" /> Campañas
                                </label>
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="cadena" class="selection" /> Cadenas Comerciales
                                </label>
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="establecimiento" class="selection" /> Tiendas
                                </label>
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="colaborador" class="selection" /> Colaboradores
                                </label>
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="voluntario" class="selection" /> Voluntarios
                                </label>
                            </div>
                        </div>

                        <button type="submit" class="btn btn--primary">Descargar datos</button>
                    </form>
                </div>
                <% }else{ %>
                <div class="content content-double">
                    <h3 class="title">Exportar Datos del Sistema</h3>

                    <form action="/perfil/exportar" method="POST">
                        <div class="data-item">
                            <label class="label-name">Tablas a exportar:</label>
                            <div class="selection-content">
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="campana" class="selection" /> Campañas
                                </label>
                                <label class="label-selection">
                                    <input type="checkbox" name="tablas" value="voluntario" class="selection" /> Voluntarios
                                </label>
                            </div>
                        </div>

                        <button type="submit" class="btn btn--primary">Descargar datos</button>
                    </form>
                </div>
                <% } %>
            </div>
        </main>
    </div>
</div>
</body>
</html>