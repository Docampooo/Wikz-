<!-- views/usuario/registro.php -->
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro - WIKZ</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap');

        body {
            margin: 0;
            font-family: 'Montserrat', sans-serif;
            background: #000000;
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            overflow: hidden;
        }

        .register-container {
            background: #111;
            padding: 40px 30px;
            border-radius: 12px;
            box-shadow: 0 0 30px rgba(128, 0, 128, 0.7);
            text-align: center;
            position: relative;
        }

        /* Bordes decorativos */
        .register-container::before,
        .register-container::after {
            content: '';
            position: absolute;
            width: 90%;
            height: 90%;
            top: 5%;
            left: 5%;
            border: 2px solid #fff;
            border-radius: 12px;
            pointer-events: none;
        }

        .register-container img {
            width: 100px;
            margin-bottom: 20px;
        }

        .register-container input {
            width: 100%;
            padding: 12px 15px;
            margin: 10px 0;
            border: none;
            border-bottom: 2px solid #fff;
            background: transparent;
            color: #fff;
            font-size: 16px;
            outline: none;
        }

        .register-container input::placeholder {
            color: #ccc;
        }

        .register-container button {
            width: 100%;
            padding: 12px;
            margin-top: 10px;
            border: none;
            background: #800080;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            border-radius: 6px;
            transition: 0.3s;
        }

        .register-container button:hover {
            background: #9b30ff;
        }

        .register-container .login-btn {
            background: #4b0082;
        }

        .register-container .login-btn:hover {
            background: #7a1fff;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <img src="assets/logo.png" alt="Logo">
        <form action="index.php?controller=Usuario&action=registro" method="post">
            <input type="text" name="nombre" placeholder="Nombre Usuario" required>
            <input type="email" name="email" placeholder="Correo Electrónico" required>
            <input type="password" name="pass" placeholder="Contraseña" required>
            <button type="submit">Sign Up</button>
            <a href="index.php?controller=Usuario&action=login"><button type="button" class="login-btn">Log In</button></a>
        </form>
    </div>
</body>
</html>