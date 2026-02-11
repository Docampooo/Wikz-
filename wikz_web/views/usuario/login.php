<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Login - WIKZ</title>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap');

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: 'Montserrat', sans-serif;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: linear-gradient(135deg, #12001f, #3a0a6a, #6a1bb1);
            background-size: 400% 400%;
            animation: gradientMove 10s ease infinite;
            color: #fff;
        }

        @keyframes gradientMove {
            0% {
                background-position: 0% 50%;
            }

            50% {
                background-position: 100% 50%;
            }

            100% {
                background-position: 0% 50%;
            }
        }

        .login-container {
            width: 380px;
            padding: 45px 35px;
            background: rgba(10, 0, 20, 0.88);
            border-radius: 20px;
            text-align: center;
            backdrop-filter: blur(8px);
            box-shadow: 0 0 30px rgba(155, 48, 255, 0.25);
        }

        .login-container img {
            width: 110px;
            margin-bottom: 25px;
            filter: drop-shadow(0 0 8px rgba(199, 125, 255, 0.4));
        }

        .login-container h2 {
            margin-bottom: 25px;
            font-weight: 700;
            letter-spacing: 1px;
        }

        /* INPUTS */
        .login-container input {
            width: 100%;
            padding: 14px 16px;
            margin: 12px 0;
            background: rgba(255, 255, 255, 0.06);
            border: 1px solid rgba(199, 125, 255, 0.35);
            border-radius: 12px;
            color: #fff;
            font-size: 15px;
            outline: none;
            transition: all 0.25s ease;
        }

        .login-container input::placeholder {
            color: #d6b9ff;
        }

        .login-container input:hover {
            border-color: #b98bff;
        }

        .login-container input:focus {
            border-color: #e0b3ff;
            background: rgba(255, 255, 255, 0.1);
            box-shadow: inset 0 0 6px rgba(224, 179, 255, 0.15);
        }

        /* BOTONES */
        .btn {
            width: 100%;
            padding: 14px;
            margin-top: 18px;
            border: none;
            border-radius: 14px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            color: #fff;
            transition: all 0.25s ease;
        }

        .btn-login {
            background: linear-gradient(135deg, #6a1bb1, #9b5de5);
        }

        .btn-login:hover {
            transform: translateY(-1px);
            background: linear-gradient(135deg, #7a2bd1, #a873f0);
        }

        .btn-register {
            margin-top: 12px;
            background: linear-gradient(135deg, #2a0845, #5a189a);
        }

        .btn-register:hover {
            transform: translateY(-1px);
            background: linear-gradient(135deg, #3a0a6a, #6a1bb1);
        }

        a {
            text-decoration: none;
        }

        /* Estilo para el mensaje de error */
        .error-msg {
            background: rgba(255, 70, 70, 0.15);
            border: 1px solid #ff4646;
            color: #ff8a8a;
            padding: 12px;
            border-radius: 10px;
            font-size: 14px;
            margin-bottom: 20px;
            animation: shake 0.4s ease-in-out;
        }

        /* Animación de vibración para resaltar el fallo */
        @keyframes shake {

            0%,
            100% {
                transform: translateX(0);
            }

            25% {
                transform: translateX(-5px);
            }

            75% {
                transform: translateX(5px);
            }
        }
    </style>
</head>

<body>

    <div class="login-container">
        <img src="/img/logosinfondo.png" alt="WIKZ logo">

        <h2>Iniciar sesión</h2>

        <?php if (isset($error)): ?>
            <div class="error-msg">
                <?= htmlspecialchars($error) ?>
            </div>
        <?php endif; ?>

        <!-- FORMULARIO LOGIN -->
        <form action="index.php?controller=Usuario&action=login" method="post">
            <input
                type="text"
                name="nombreUs" placeholder="Nombre de usuario"
                required>

            <input
                type="password"
                name="passUs" placeholder="Contraseña"
                required>

            <button type="submit" class="btn btn-login">
                Entrar
            </button>
        </form>

        <!-- REDIRECCIÓN A REGISTRO -->
        <a href="index.php?controller=Usuario&action=registro">
            <button type="button" class="btn btn-register">
                Crear cuenta
            </button>
        </a>
    </div>

</body>

</html>