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
            background: linear-gradient(135deg, #1a002b, #4b0082);
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .register-container {
            background: rgba(20, 10, 30, 0.95);
            padding: 40px 35px;
            border-radius: 14px;
            width: 380px;
            box-shadow: 0 0 25px rgba(128, 0, 128, 0.4);
            text-align: center;
            position: relative;
        }

        .register-container img {
            width: 90px;
            margin-bottom: 20px;
        }

        .register-container h2 {
            margin-bottom: 20px;
            font-weight: 600;
        }

        .register-container input {
            width: 100%;
            padding: 12px 14px;
            margin: 10px 0;
            border: none;
            border-radius: 6px;
            background: #2a143d;
            color: #fff;
            font-size: 15px;
            outline: none;
            transition: all 0.3s ease;
        }

        .register-container input::placeholder {
            color: #cbbcdc;
        }

        .register-container input:focus {
            background: #361a55;
            box-shadow: 0 0 0 1px #8f5cff;
        }

        .error {
            color: #ff8a8a;
            font-size: 13px;
            margin-top: 5px;
            display: none;
        }

        .register-container button {
            width: 100%;
            padding: 12px;
            margin-top: 18px;
            border: none;
            background: #5a189a;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            border-radius: 6px;
            transition: background 0.3s ease;
        }

        .register-container button:hover {
            background: #6f2dbd;
        }

        .login-link {
            margin-top: 15px;
            display: block;
            font-size: 14px;
            color: #d1b3ff;
            text-decoration: none;
        }

        .login-link:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

    <div class="register-container">
        <img src="/img/logosinfondo.png" alt="WIKZ">
        <h2>Crear cuenta</h2>

        <?php if (!empty($error)): ?>
            <div class="error" style="display:block;">
                <?= htmlspecialchars($error) ?>
            </div>
        <?php endif; ?>

        <form id="registroForm" action="index.php?controller=Usuario&action=registro" method="post">
            <input type="text" name="nombre" placeholder="Nombre de usuario" required>
            <input type="email" name="email" placeholder="Correo electrónico" required>
            <input type="password" name="pass" placeholder="Contraseña" required>
            <input type="password" id="pass2" placeholder="Repetir contraseña" required>

            <div id="errorPass" class="error">
                Las contraseñas no coinciden
            </div>

            <button type="submit">Crear cuenta</button>
        </form>

        <a class="login-link" href="index.php?controller=Usuario&action=login">
            ¿Ya tienes cuenta? Inicia sesión
        </a>
    </div>

    <script>
        const form = document.getElementById('registroForm');
        const pass = form.querySelector('input[name="pass"]');
        const pass2 = document.getElementById('pass2');
        const errorPass = document.getElementById('errorPass');

        form.addEventListener('submit', function(e) {
            if (pass.value !== pass2.value) {
                e.preventDefault();
                errorPass.style.display = 'block';
            } else {
                errorPass.style.display = 'none';
            }
        });
    </script>

</body>

</html>