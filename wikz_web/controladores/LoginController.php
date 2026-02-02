<?php
require_once "models/UsuarioModel.php";

class LoginController
{
    private usuario_model $usuarioModel;

    public function __construct()
    {
        $this->usuarioModel = new usuario_model();
    }

    // 1️⃣ Mostrar formulario
    public function mostrar()
    {
        require "vistas/vista_iniciarSecion";
    }

    // 2️⃣ Procesar login
    public function login()
    {
        $nombre = $_POST["nombre"] ?? "";
        $pass = $_POST["pass"] ?? "";

        $usuario = $this->usuarioModel->login($nombre, $pass);

        if ($usuario) {
            $_SESSION["usuario"] = $usuario;
            header("Location: index.php?c=Home&m=mostrar");
            exit;
        }

        $error = "Usuario o contraseña incorrectos";
        require "vistas/vista_login.php";
    }

    // 3️⃣ Cerrar sesión
    public function logout()
    {
        session_destroy();
        header("Location: index.php");
        exit;
    }
}