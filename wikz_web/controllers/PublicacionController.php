<?php
// controllers/PublicacionController.php
require_once 'models/Publicacion.php';

class PublicacionController {

    public function crear() {
        session_start();
        $id_usuario = $_SESSION['usuario'] ?? 0;

        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $titulo = $_POST['titulo'];
            $descripcion = $_POST['descripcion'];
            $imagen = file_get_contents($_FILES['imagen']['tmp_name'] ?? '');

            $pub = new Publicacion();
            $pub->crear($id_usuario, $titulo, $descripcion, $imagen);

            header("Location: index.php?controller=Publicacion&action=lista");
            exit;
        }

        require 'views/publicacion/crear.php';
    }

    public function lista() {
        session_start();
        $id_usuario = $_SESSION['usuario'] ?? 0;

        $pub = new Publicacion();
        $publicaciones = $pub->getPorUsuario($id_usuario);

        require 'views/publicacion/lista.php';
    }
}