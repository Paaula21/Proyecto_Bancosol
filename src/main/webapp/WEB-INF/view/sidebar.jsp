<%--
Página JSP que proporciona el menú lateral de todas las páginas
Autores:
- Andrea Pérez Rodríguez: 95%
- IA Generativa: 5%
--%>

<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("user");
    String currentURI = request.getRequestURI().toLowerCase();
%>

<head>
    <link rel="stylesheet" href="/css/Sidebar.css">
</head>

<aside class="sidebar">
    <!-- PERFIL -->
    <a href="/perfil" class="enlace-perfil" title="Perfil de usuario">
        <div class="user-block">
            <!-- Primera letra del usuario para el Avatar, sacado de la ia -->
            <div class="avatar avatar-jc"><%= usuario.getUsuario().substring(0,1).toUpperCase() %></div>
            <div class="user-info">
                <p class="user-name"><%= usuario.getUsuario() %></p>
                <p class="user-role"><%= usuario.getNombreRol() %></p>
            </div>
        </div>
    </a>

    <h2 class="menu-heading">MENÚ PRINCIPAL</h2>
    <ul id="menu">
        <li class="<%= currentURI.contains("administrador") ? "active" : "" %>">
            <a href="/dashboard">Dashboard</a>
        </li>

        <% int idRol = usuario.getIdRol(); %>

        <!-- ADMINISTRADORES Y COORDINADORES -->
        <% if (idRol == 1 || idRol == 2) { %>
        <li class="<%= currentURI.contains("/campanas") ? "active" : "" %>">
            <a href="/campanas">Campañas</a>
        </li>
        <li class="<%= currentURI.contains("/cadenas") ? "active" : "" %>">
            <a href="/cadenas">Cadenas</a>
        </li>
        <li class="<%= currentURI.contains("/voluntarios") ? "active" : "" %>">
            <a href="/voluntarios">Voluntarios</a>
        </li>
        <li class="<%= currentURI.contains("/incidencias") ? "active" : "" %>">
            <a href="/incidencias">Incidencias</a>
        </li>
        <% } %>

        <!-- ADMINISTRADOREs -->
        <% if (idRol == 1) { %>
        <li class="<%= currentURI.contains("/tienda") ? "active" : "" %>">
            <a href="/tiendas">Tiendas</a>
        </li>
        <li class="<%= currentURI.contains("/colaboradores") ? "active" : "" %>">
            <a href="/colaboradores">Colaboradores</a>
        </li>
        <% } %>

        <!-- COLABORADORES -->
        <% if (idRol == 3) { %>
        <li class="<%= currentURI.contains("/campanas") ? "active" : "" %>">
            <a href="/campanas">Campañas</a>
        </li>
        <li class="<%= currentURI.contains("/voluntarios") ? "active" : "" %>">
            <a href="/voluntarios">Voluntarios</a>
        </li>
        <% } %>
    </ul>

    <div class="bottom-menu">
        <ul>
            <li><a href="/salir">Cerrar Sesión</a></li>
        </ul>
    </div>
</aside>