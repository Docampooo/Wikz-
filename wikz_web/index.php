<?php
// =========================================================================
// 1. CONFIGURACIÓN Y AUTOCARGA
// =========================================================================
require_once 'config/Database.php';

spl_autoload_register(function ($className) {
    if (file_exists('controllers/' . $className . '.php')) {
        require_once 'controllers/' . $className . '.php';
    } elseif (file_exists('models/' . $className . '.php')) {
        require_once 'models/' . $className . '.php';
    }
});

// =========================================================================
// 2. ENRUTADOR (Lógica de Controladores)
// =========================================================================
$controller = $_GET['controller'] ?? 'Usuario';
$action     = $_GET['action'] ?? 'login';

$controllerName = $controller . 'Controller';

if (class_exists($controllerName)) {
    $ctrl = new $controllerName();
    if (method_exists($ctrl, $action)) {
        // Ejecuta la lógica del controlador (Login, Registro o Main)
        $ctrl->$action();
    } else {
        die("Acción no encontrada: $action");
    }
} else {
    die("Controlador no encontrado: $controllerName");
}

// Si la acción no es 'main', el script termina aquí (se cargan las vistas de login/registro)
if ($action !== 'main') {
    exit;
}
?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>Wikz - Social App</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body class="dark-theme">

    <header class="main-header">
        <h1 class="logo">WIKZ</h1>
    </header>

    <main id="app-content">
    </main>

    <nav class="bottom-nav">
        <div class="nav-item" onclick="navegar('explorar', this)" id="nav-explorar">
            <span class="material-icons">explore</span>
            <p>Explorar</p>
        </div>
        <div class="nav-item" onclick="navegar('crear', this)" id="nav-crear">
            <span class="material-icons">add_circle</span>
            <p>Publicar</p>
        </div>
        <div class="nav-item" onclick="navegar('perfil', this)" id="nav-perfil">
            <span class="material-icons">account_circle</span>
            <p>Perfil</p>
        </div>
    </nav>

    <script>
        // 1. Recogemos los datos que PHP guardó en la sesión
        // Usamos el operador ternario para verificar si existe la sesión
        <?php
        if (session_status() === PHP_SESSION_NONE) session_start();
        $userSesion = $_SESSION['usuario'] ?? null;
        ?>

        // 2. Pasamos esos datos a JavaScript de forma segura
        const usuarioDesdePHP = <?php echo $userSesion ? json_encode($userSesion) : 'null'; ?>;

        if (usuarioDesdePHP) {
            const sesionLocal = JSON.parse(localStorage.getItem('wikz_session'));

            // Solo actualizamos el localStorage si NO existe una sesión previa 
            // o si el ID es diferente (cambio de cuenta)
            if (!sesionLocal || sesionLocal.id !== usuarioDesdePHP.id) {
                const nuevaSesion = {
                    id: usuarioDesdePHP.id,
                    nombre: usuarioDesdePHP.nombre,
                    biografia: usuarioDesdePHP.biografia,
                    timestamp: Date.now()
                };
                localStorage.setItem('wikz_session', JSON.stringify(nuevaSesion));
            }
        }

        // 3. Crear la variable global que usará tu app.js
        const WIKZ_USER = JSON.parse(localStorage.getItem('wikz_session')) || {
            id: 0,
            nombre: 'Invitado',
            biografia: ''
        };

        console.log("Sesión activa de Wikz:", WIKZ_USER);
    </script>

    <script src="assets/js/app.js"></script>

    <script>
        // C) Al cargar la página por completo, mostramos la sección Explorar
        window.onload = () => {
            const btnExplorar = document.getElementById('nav-explorar');
            navegar('explorar', btnExplorar);
        };
    </script>

</body>

</html>