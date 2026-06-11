<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("user");
    String currentURI = request.getRequestURI();
%>

<head>
    <link rel="stylesheet" href="/css/Sidebar.css">
</head>

<aside class="sidebar">
    <a href="/perfil" class="enlace-perfil">
        <div class="user-block">
            <div class="avatar avatar-jc"><%= usuario.getUsuario().substring(0,1).toUpperCase() %></div>
            <div class="user-info">
                <p class="user-name"><%= usuario.getUsuario() %></p>
                <p class="user-role"><%= usuario.getRol().getNombreRol() %></p>
            </div>
        </div>
    </a>

    <h2 class="menu-heading">MENÚ PRINCIPAL</h2>
    <ul id="menu">
        <li class="<%= currentURI.contains("/dashboard") ? "active" : "" %>">
            <a href="/dashboard">Dashboard</a>
        </li>

        <% if (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2) { %>
        <li class="<%= currentURI.contains("/campanas") ? "active" : "" %>">
            <a href="/campanas">Campañas</a>
        </li>
        <% } %>

        <% if (usuario.getRol().getIdRol() == 1) { %>
        <li class="<%= currentURI.contains("/tiendas") ? "active" : "" %>">
            <a href="/tiendas">Tiendas</a>
        </li>
        <% } %>
    </ul>

    <div class="bottom-menu">
        <ul>
            <li><a href="/salir">Cerrar Sesión</a></li>
        </ul>
    </div>
</aside>