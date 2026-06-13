<!--
Ainhoa García Rebollo: 100%
-->


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/Administrador.css">
    <link rel="stylesheet" href="../css/EditarVoluntario.css">
    <link rel="stylesheet" href="../css/popUpRegistro.css">
    <link rel="stylesheet" href="../css/RegistroVoluntarios.css">
    <link rel="stylesheet" href="../css/Header.css">
    <link rel="stylesheet" href="../css/Sidebar.css">
    <link rel="stylesheet" href="../css/Common.css">

    <title>Editar Voluntario</title>
</head>

<body>
<div class="main-container">

    <jsp:include page="sidebar.jsp" />

    <div class="right-container">

        <jsp:include page="header.jsp">
            <jsp:param name="titulo" value="Editar Voluntarios" />
            <jsp:param name="subtitulo" value="Modificación de datos o disponibilidad del voluntario" />
        </jsp:include>

        <div class="form-container">
            <form action="/voluntarios/guardar" method="POST" id="form-edit-voluntario">

                <input type="hidden" name="id" value="${voluntarioActual != null ? voluntarioActual.idVoluntario : ''}">

                <div class="list-container">
                    <div class="list-header">
                        <h2>Datos Personales</h2>
                        <br>
                        <p>Modifique la información del voluntario</p>
                    </div>

                    <div class="field-group">
                        <label for="nombre">Nombre Completo</label>
                        <input type="text" id="nombre" name="nombre"
                               value="${voluntarioActual != null ? voluntarioActual.nombreCompleto : ''}" required>
                    </div>

                    <div class="field-group">
                        <label for="email">Correo Electrónico</label>
                        <input type="email" id="email" name="email"
                               value="${voluntarioActual != null ? voluntarioActual.email : ''}" required>
                    </div>

                    <div class="field-group">
                        <label for="telefono">Teléfono</label>
                        <input type="number" id="telefono" name="telefono"
                               value="${voluntarioActual != null ? voluntarioActual.telefono : ''}">
                    </div>
                </div>

                <div class="list-container">
                    <div class="list-header">
                        <h2>Disponibilidad en la campaña</h2>
                        <br>
                        <p>Especifique la disponibilidad del voluntario para la campaña</p>
                    </div>

                    <div class="field-group" style="padding: 10px 0;">
                        <select id="disponibilidad-select" name="disponibilidad" required style="width: 100%; padding: 10px; border: 1px solid #d1d5db; border-radius: 6px;">
                            <option value="" disabled ${voluntarioActual == null ? 'selected' : ''}>-- Seleccione un turno --</option>
                            <option value="mañana" ${voluntarioActual != null && voluntarioActual.disponibilidad == 'mañana' ? 'selected' : ''}>Turno de Mañana</option>
                            <option value="tarde" ${voluntarioActual != null && voluntarioActual.disponibilidad == 'tarde' ? 'selected' : ''}>Turno de Tarde</option>
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
        </div>
    </div>
</div>

</body>
</html>