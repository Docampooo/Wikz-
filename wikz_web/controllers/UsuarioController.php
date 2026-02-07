<?php
// controllers/UsuarioController.php
require_once 'models/Usuario.php';

class UsuarioController
{

    public function login()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $nombre = $_POST['nombre'];
            $pass  = $_POST['password'];

            $usuario = new Usuario();
            $data = $usuario->getByNombre($nombre);

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

    public function registro()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {

            $nombre = trim($_POST['nombre']);
            $email  = trim($_POST['email']);
            $pass   = $_POST['pass'];

            $usuario = new Usuario();

            // 🔎 Comprobar email
            if ($usuario->getByEmail($email)) {
                $error = "El correo electrónico ya está registrado";
                require 'views/usuario/registro.php';
                return;
            }

            // 🔎 Comprobar nombre
            if ($usuario->getByNombre($nombre)) {
                $error = "El nombre de usuario ya existe";
                require 'views/usuario/registro.php';
                return;
            }

            //Crear usuario
            $usuario->crear($nombre, $email, $pass);

            //Redirigir al login
            header("Location: index.php?controller=Usuario&action=login");
            exit;
        }

        require 'views/usuario/registro.php';
    }
}
