<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Acciones</title>
    <link rel="stylesheet" href="../css/Historial.css">
    <link rel="stylesheet" href="../css/Common.css">
</head>
<body>

<div class="historial-background">
    <div class="historial-container">
        <div class="historial-header">
            <h2 class="historial-titulo">Registro de Acciones</h2>
        </div>

        <ul class="historial-lista">

            <li class="historial-item">
                <div class="historial-info">
                    <span class="historial-accion">Campaña Verano se ha creado</span>
                </div>
                <div class="historial-fecha">
                    12 de junio de 2026 a las 10:30
                </div>
            </li>

            <li class="historial-item">
                <div class="historial-info">
                    <span class="historial-accion">Recogida de Alimentos se ha modificado</span>
                </div>
                <div class="historial-fecha">
                    11 de junio de 2026 a las 16:45
                </div>
            </li>

            <li class="historial-item">
                <div class="historial-info">
                    <span class="historial-accion">Campaña Navidad se ha eliminado</span>
                </div>
                <div class="historial-fecha">
                    10 de junio de 2026 a las 09:15
                </div>
            </li>

        </ul>

        <div style="margin-top: 20px; text-align: center;">
            <button class="btn btn--primary" onclick="window.history.back()">Volver a Campañas</button>
        </div>

    </div>
</div>

</body>
</html>