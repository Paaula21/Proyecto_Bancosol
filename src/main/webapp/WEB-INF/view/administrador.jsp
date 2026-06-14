<%--
Autores:
- Andrea Pérez Rodríguez: 90%
- IA Generativa: 10%
--%>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.CoberturaZonaDTO" %>
<%@ page import="es.uma.tesaw.proyecto_bancosol.dto.UsuarioDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer campanasActivasCount = (Integer) request.getAttribute("campanasActivasCount");
    Long tiendasTotales = (Long) request.getAttribute("tiendasTotales");
    Long zonasTotales = (Long) request.getAttribute("zonasTotales");
    Long colaboradoresTotales = (Long) request.getAttribute("colaboradoresTotales");
    Integer coordinadoresTotales = (Integer) request.getAttribute("coordinadoresTotales");
    List<CampanaDTO> proximasCampanas = (List<CampanaDTO>) request.getAttribute("proximasCampanas");
    List<CoberturaZonaDTO> coberturasZona = (List<CoberturaZonaDTO>) request.getAttribute("coberturasZona");
    UsuarioDTO user = (UsuarioDTO) session.getAttribute("user");
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
                        <% if (user.getIdRol() == 1 | user.getIdRol() == 2) { %>
                        <button class="btn-view-all" onclick="window.location.href='/campanas'">Ver todas</button>
                        <% } %>
                    </div>

                    <div class="campaign-container" id="campaign-container">
                        <%
                            if (proximasCampanas != null && !proximasCampanas.isEmpty()) {
                                for (CampanaDTO campana : proximasCampanas) {
                                    String fechaInicioStr = (campana.getFechaInicio() != null) ? campana.getFechaInicio().toString() : "Sin fecha";
                                    String fechaFinStr = (campana.getFechaFin() != null) ? campana.getFechaFin().toString() : "Sin fecha";
                        %>
                        <div class="campaign-item">
                            <div class="campaign-info">
                                <h4><%= campana.getNombreCampana() %></h4>
                                <span> Desde <%= fechaInicioStr %> hasta <%= fechaFinStr %></span>
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
                        <%
                            if (coberturasZona != null && !coberturasZona.isEmpty()) {
                                for (CoberturaZonaDTO cob : coberturasZona) {
                        %>
                        <div class="shops-item">
                            <div class="shop-labels">
                                <span><%= cob.getNombreZona() %></span>
                                <span><%= cob.getTiendas() %> tiendas</span>
                            </div>
                            <div class="progress-bar-container">
                                <!-- Usamos data-target-width para que JS sepa hasta dónde animar -->
                                <div class="progress-bar" data-target-width="<%= cob.getPorcentaje() %>%" style="width: 0%;"></div>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <p>No hay datos de cobertura.</p>
                        <%
                            }
                        %>
                    </div>
                </div>

            </section>

        </main>
    </div>
</div>

<script>
    <!-- Script generado por ia para añadir una animación en la cobertura por zona al iniciar la página -->
    document.addEventListener("DOMContentLoaded", function () {
        const barras = document.querySelectorAll('.progress-bar');

        barras.forEach(function(barra, indice) {
            const anchoObjetivo = barra.getAttribute('data-target-width');

            // Retardo escalonado (100ms inicial + 80ms por cada barra extra)
            setTimeout(function () {
                barra.style.width = anchoObjetivo;
            }, 100 + (indice * 80));
        });
    });
</script>

</body>
</html>