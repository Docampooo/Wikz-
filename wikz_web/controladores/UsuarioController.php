<?php
// controllers/UsuarioController.php
require_once 'models/Usuario.php';

class UsuarioController {

    public function login() {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $email = $_POST['email'];
            $pass  = $_POST['pass'];

            $usuario = new Usuario();
            $data = $usuario->getByEmail($email);

            if ($data && password_verify($pass, $data['pass'])) {
                session_start();
                $_SESSION['usuario'] = $data['id'];
                header("Location: index.php?controller=Publicacion&action=lista");
                exit;
            } else {
                $error = "Usuario o contraseña incorrecta";
            }
        }
        require 'views/usuario/login.php';
    }

    public function registro() {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $nombre = $_POST['nombre'];
            $email = $_POST['email'];
            $pass = $_POST['pass'];

            $usuario = new Usuario();
            $usuario->crear($nombre, $email, $pass);

            header("Location: index.php?controller=Usuario&action=login");
            exit;
        }
        require 'views/usuario/registro.php';
    }
}