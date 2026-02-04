<?php
// index.php
require_once 'config/Database.php';

// Autocarga de controladores y modelos
spl_autoload_register(function($className) {
    if (file_exists('controllers/' . $className . '.php')) {
        require_once 'controllers/' . $className . '.php';
    } elseif (file_exists('models/' . $className . '.php')) {
        require_once 'models/' . $className . '.php';
    }
});

//Rutas principales
$controller = $_GET['controller'] ?? 'Usuario';
$action     = $_GET['action'] ?? 'login';

$controllerName = $controller . 'Controller';
if (class_exists($controllerName)) {
    $ctrl = new $controllerName();
    if (method_exists($ctrl, $action)) {
        $ctrl->$action();
    } else {
        echo "Acción no encontrada: $action";
    }
} else {
    echo "Controlador no encontrado: $controllerName";
}