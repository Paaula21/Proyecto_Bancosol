<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <!-- Cambiar la hiperreferencia a la correcta -->
    <link rel="stylesheet" href="/css/Login.css">
    <title>Ventana de inicio</title>
</head>
<body>
<div class="main-container">
    <div class="div-image">
        <img src="/icono.png" alt="Logo de BancoSol">
        <h1>Inicio de sesión</h1>
    </div>
    <fieldset class="login-container">
        <form method="POST" action="/login" class="form-login" id="form-login">
            <!-- Añadimos los parámetros autocomplete para la opción de recordarme -->
            <label for="username">Nombre de usuario</label><br>
            <input type="text" id="username" name="username" placeholder="Ej: Cristóbal" required autocomplete="username" /><br><br>

            <label for="password">Contraseña</label><br>
            <input type="password" id="password" name="password" required autocomplete="current-password" /><br><br>

            <div class="other-options">
                <label class="remember">
                    <input type="checkbox" name="remember" id="remember"> Recordarme
                </label>
                <br>
                <a href="#" class="forget-password" id="forgot-password">¿Olvidaste tu contraseña?</a>
            </div>
            <br>
            <button type="submit">Inicio de sesión</button>
        </form>
    </fieldset>
    <!-- Esto muestra mensajes de error o éxito -->
    <p id="message"></p>
</div>
</body>
</html>