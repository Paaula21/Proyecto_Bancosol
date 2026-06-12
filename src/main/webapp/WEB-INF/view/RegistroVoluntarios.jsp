<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de voluntarios</title>

    <link rel="stylesheet" href="../css/RegistroVoluntarios.css">
    <link rel="stylesheet" href="../css/popUpRegistro.css">
    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/EditarVoluntario.css">
    <link rel="stylesheet" href="../css/Common.css">
</head>

<body>

<div class="app-container">

    <jsp:include page="sidebar.jsp" />

    <div class="right-content">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Registro de Voluntarios" />
            <jsp:param name="subtitulo" value="Introduzca la información del voluntario para hacer su registro" />
        </jsp:include>

        <div class="main-layout">

            <main>
                <form id="form-voluntario" action="/voluntarios/guardar" method="POST">

                    <input type="hidden" name="id" value="${voluntarioActual != null ? voluntarioActual.idVoluntario : ''}">

                    <div class="list-container">
                        <div class="list-header">
                            <h2>Información del Voluntario</h2>
                            <br>
                            <p>${editando ? 'Editar los datos del voluntario' : 'Añadir un nuevo voluntario a la base de datos'}</p>
                        </div>

                        <div class="field-group">
                            <label for="volunt-name">Nombre</label>
                            <input type="text" id="volunt-name" name="nombre"
                                   value="${(voluntarioActual != null && voluntarioActual.persona != null) ? voluntarioActual.persona.nombreCompleto : ''}" required>
                        </div>

                        <div class="field-group">
                            <label for="volunt-email">Email</label>
                            <input type="email" id="volunt-email" name="email"
                                   value="${(voluntarioActual != null && voluntarioActual.persona != null) ? voluntarioActual.persona.email : ''}" required>
                        </div>

                        <div class="field-group">
                            <label for="volunt-tel">Teléfono (opcional)</label>
                            <input type="number" id="volunt-tel" name="telefono"
                                   value="${(voluntarioActual != null && voluntarioActual.persona != null) ? voluntarioActual.persona.telefono : ''}">
                        </div>
                    </div>

                    <div class="list-container">
                        <div class="list-header">
                            <h2>Disponibilidad en la campaña</h2>
                            <br>
                            <p>Especifique la preferencia horaria del voluntario</p>
                        </div>

                        <div class="field-group" style="padding: 10px 0;">
                            <label for="disponibilidad-select">Turno preferente</label>
                            <select id="disponibilidad-select" name="disponibilidad" required style="width: 100%; padding: 10px; border: 1px solid #d1d5db; border-radius: 6px;">
                                <option value="" disabled ${voluntarioActual == null ? 'selected' : ''}>-- Seleccione un turno --</option>
                                <option value="mañana" ${voluntarioActual != null && voluntarioActual.preferenciaHorario == 'mañana' ? 'selected' : ''}>Turno de Mañana</option>
                                <option value="tarde" ${voluntarioActual != null && voluntarioActual.preferenciaHorario == 'tarde' ? 'selected' : ''}>Turno de Tarde</option>
                            </select>
                        </div>
                    </div>

                    <div class="contenedor" style="display: flex; gap: 15px;">
                        <article>
                            <button type="submit" class="btn btn--primary" id="btn-guardar">
                                Guardar
                            </button>
                        </article>
                        <article>
                            <button type="button" class="btn btn--cancel" id="btn-descartar" onclick="window.location.href='/voluntarios'">
                                Cancelar
                            </button>
                        </article>
                    </div>
                </form>
            </main>
        </div>
    </div>
</div>

</body>
</html>