<?php
session_start();

$c = $_GET["c"] ?? "Login";     // controlador por defecto
$m = $_GET["m"] ?? "mostrar";   // método por defecto

$controllerName = $c . "Controller";
$controllerFile = "controllers/$controllerName.php";

if (!file_exists($controllerFile)) {
    die("Controlador no encontrado");
}

require_once $controllerFile;

$controller = new $controllerName();

if (!method_exists($controller, $m)) {
    die("Método no encontrado");
}

$controller->$m();