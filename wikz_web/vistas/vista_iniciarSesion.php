<!DOCTYPE html>
<html lang="en">

<!-- Index es el controlador primario -->

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Wikz · Login</title>
    <link rel="stylesheet" href="/estilos/login.css">
</head>

<body>

    <div class="login-container">
        <div class="card">

            <div class="logo">
                <img src="/img/logosinfondo.png" alt="Wikz">
            </div>

            <form method="POST" action="/vistas/vista_principal.php">
                <input type="text" name="usuario" placeholder="Nombre Usuario" required>
                <input type="password" name="password" placeholder="Contraseña" required>

                <button type="submit" class="btn login">Log In</button>
                <button type="button" class="btn signup"
                    onclick="location.href='/vistas/iniciar_registro.php'">
                    Sign Up
                </button>
            </form>

        </div>
    </div>

</body>

</html>