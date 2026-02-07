<?php
// controllers/ColeccionController.php
require_once 'models/Coleccion.php';

class ColeccionController {

    public function crear() {
        session_start();
        $id_usuario = $_SESSION['usuario'] ?? 0;

        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $titulo = $_POST['titulo'];
            $imagen = file_get_contents($_FILES['imagen']['tmp_name'] ?? '');

            $coleccion = new Coleccion();
            $coleccion->crear($id_usuario, $titulo, $imagen);

            header("Location: index.php?controller=Coleccion&action=lista");
            exit;
        }

        require 'views/coleccion/crear.php';
    }

    public function lista() {
        session_start();
        $id_usuario = $_SESSION['usuario'] ?? 0;

        $coleccion = new Coleccion();
        $colecciones = $coleccion->getPorUsuario($id_usuario);

        require 'views/coleccion/lista.php';
    }

    public function addPublicacion() {
        $id_coleccion = $_POST['id_coleccion'];
        $id_publicacion = $_POST['id_publicacion'];

        $coleccion = new Coleccion();
        $coleccion->addPublicacion($id_coleccion, $id_publicacion);

        header("Location: index.php?controller=Coleccion&action=detalle&id=$id_coleccion");
        exit;
    }

    public function detalle() {
        $id_coleccion = $_GET['id'] ?? 0;
        $coleccion = new Coleccion();
        $info = $coleccion->getById($id_coleccion);
        $publicaciones = $coleccion->getPublicaciones($id_coleccion);

        require 'views/coleccion/detalle.php';
    }
}