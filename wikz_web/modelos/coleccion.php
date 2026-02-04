<?php
// models/Coleccion.php
require_once 'config/Database.php';

class Coleccion
{
    private $pdo;

    public $id;
    public $id_usuario;
    public $titulo;
    public $imagen;
    public $fecha_creacion;

    public function __construct()
    {
        $this->pdo = Database::getConnection();
    }

    // Crear colección
    public function crear($id_usuario, $titulo, $imagen = null)
    {
        $stmt = $this->pdo->prepare(
            "INSERT INTO colecciones (id_usuario, titulo, imagen, creacion) VALUES (?, ?, ?, NOW())"
        );
        return $stmt->execute([$id_usuario, $titulo, $imagen]);
    }

    // Obtener colecciones de un usuario
    public function getPorUsuario($id_usuario)
    {
        $stmt = $this->pdo->prepare("SELECT * FROM colecciones WHERE id_usuario = ? ORDER BY creacion DESC");
        $stmt->execute([$id_usuario]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // Obtener colección por id
    public function getById($id)
    {
        $stmt = $this->pdo->prepare("SELECT * FROM colecciones WHERE id = ?");
        $stmt->execute([$id]);
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    // Añadir publicación a la colección (guardados)
    public function addPublicacion($id_coleccion, $id_publicacion)
    {
        $stmt = $this->pdo->prepare(
            "INSERT INTO guardados (id_coleccion, id_publicacion) VALUES (?, ?)"
        );
        return $stmt->execute([$id_coleccion, $id_publicacion]);
    }

    // Obtener publicaciones de la colección
    public function getPublicaciones($id_coleccion)
    {
        $stmt = $this->pdo->prepare(
            "SELECT p.* FROM publicaciones p
             JOIN guardados g ON p.id = g.id_publicacion
             WHERE g.id_coleccion = ? ORDER BY p.creacion DESC"
        );
        $stmt->execute([$id_coleccion]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}
