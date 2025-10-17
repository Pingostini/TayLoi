<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema Logística</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #14532d, #4ade80);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            display: flex;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.2);
            overflow: hidden;
            max-width: 850px;
            width: 100%;
        }

        /* Columna izquierda (formulario + logo) */
        .login-form {
            flex: 1;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-form img {
            width: 150px;
            margin: 0 auto 20px;
            display: block;
        }

        .login-form p {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
            font-size: 14px;
        }

        .login-form label {
            font-size: 14px;
            font-weight: bold;
            margin-bottom: 6px;
            display: block;
            color: #333;
        }

        .login-form input[type="text"],
        .login-form input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            outline: none;
            transition: 0.2s;
        }

        .login-form input[type="text"]:focus,
        .login-form input[type="password"]:focus {
            border-color: #14532d;
        }

        .login-form input[type="submit"] {
            background-color: #14532d;
            color: #fff;
            font-size: 15px;
            font-weight: bold;
            padding: 12px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.3s;
            width: 100%;
        }

        .login-form input[type="submit"]:hover {
            background-color: #064e3b;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }

        /* Columna derecha (imagen logística) */
        .login-image {
            flex: 1;
            background: url('img/logistica.jpg') no-repeat center center;
            background-size: cover;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <!-- Columna izquierda -->
        <div class="login-form">
            <!-- Logo Tai Loy -->
            <img src="img/logo.png" alt="Tai Loy">
            <p>Ingrese sus credenciales para acceder al sistema</p>
            <form action="LoginServlet" method="post">
                <label for="usuario">Usuario</label>
                <input type="text" name="usuario" id="usuario" placeholder="Ingrese su usuario" required>

                <label for="clave">Contraseña</label>
                <input type="password" name="clave" id="clave" placeholder="Ingrese su contraseña" required>

                <input type="submit" value="Iniciar Sesión">
            </form>
            <% 
                String error = request.getParameter("error");
                if (error != null) { 
            %>
                <p class="error-message">Usuario o clave incorrectos</p>
            <% } %>
        </div>

        <!-- Columna derecha con la imagen logística -->
        <div class="login-image"></div>
    </div>
</body>
</html>
