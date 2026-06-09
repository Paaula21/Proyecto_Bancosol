<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.entities.Campana" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer campanasActivasCount = (Integer) request.getAttribute("campanasActivasCount");
    Long tiendasTotales = (Long) request.getAttribute("tiendasTotales");
    Long zonasTotales = (Long) request.getAttribute("zonasTotales");
    Long colaboradoresTotales = (Long) request.getAttribute("colaboradoresTotales");
    Integer coordinadoresTotales = (Integer) request.getAttribute("coordinadoresTotales");
    List<Campana> proximasCampanas = (List<Campana>) request.getAttribute("proximasCampanas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Vista de administrador</title>
    <link rel="stylesheet" href="/css/Administrador.css">
    <link rel="stylesheet" href="/css/Sidebar.css">
    <link rel="stylesheet" href="/css/Header.css">
</head>
<body>
<div class="main-container">

    <jsp:include page="sidebar.jsp" />

    <div class="right-container">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Dashboard" />
            <jsp:param name="subtitulo" value="Resumen general de los datos" />
        </jsp:include>

        <main class="dashboard-container">

            <section class="grid">
                <div class="card">
                    <h3>Campañas Activas</h3>
                    <div class="card" id="stat-campaigns-value"><%= campanasActivasCount %></div>
                </div>
                <div class="card">
                    <h3>Tiendas Totales</h3>
                    <div class="card" id="stat-stores-value"><%= tiendasTotales %></div>
                    <p class="card" id="stat-stores-subtitle">En <%= zonasTotales %> zonas geográficas</p>
                </div>
                <div class="card">
                    <h3>Colaboradores</h3>
                    <div class="card" id="stat-collaborators-value"><%= colaboradoresTotales %></div>
                    <p class="card" id="stat-collaborators-subtitle">Entidades y organizaciones</p>
                </div>
                <div class="card">
                    <h3>Coordinadores</h3>
                    <div class="card" id="stat-coordinators-value"><%= coordinadoresTotales %></div>
                    <p class="card" id="stat-coordinators-subtitle">Activos en campaña</p>
                </div>
            </section>

            <section class="dashboard-middle">

                <div class="upcoming-campaigns">
                    <div class="header-upcoming-campaigns">
                        <div>
                            <h2>Próximas Campañas</h2>
                            <p>Campañas programadas</p>
                        </div>
                        <button class="btn-view-all" onclick="window.location.href='/campanas'">Ver todas</button>
                    </div>

                    <div class="campaign-container" id="campaign-container">
                        <%
                            if (proximasCampanas != null && !proximasCampanas.isEmpty()) {
                                for (Campana campana : proximasCampanas) {
                                    String fechaInicioStr = (campana.getFechaInicio() != null) ? campana.getFechaInicio().toString() : "Sin fecha";
                                    String fechaFinStr = (campana.getFechaFin() != null) ? campana.getFechaFin().toString() : "Sin fecha";
                        %>
                        <div class="campaign-item">
                            <div class="campaign-info">
                                <h4><%= campana.getNombreCampana() %></h4>
                                <span><%= fechaInicioStr %> - <%= fechaFinStr %></span>
                            </div>
                            <div class="campaign-stats">
                                <div class="stat">
                                    <strong><%= campana.getEstado() %></strong>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <p>No hay campañas próximas.</p>
                        <%
                            }
                        %>
                    </div>
                </div>

                <div class="zone-coverage panel">
                    <div class="panel-header">
                        <h2>Cobertura por Zona</h2>
                        <p>Número de tiendas por zona</p>
                    </div>
                    <div class="shops-list" id="shops-list">
                    </div>
                </div>

            </section>

        </main>
    </div>
</div>
</body>
</html>