<?php

// models/Publicacion.php
require_once 'config/Database.php';

class Publicacion
{
    private $pdo;

    public $id;
    public $id_usuario;
    public $titulo;
    public $descripcion;
    public $imagen;
    public $fecha_creacion;

    public function __construct()
    {
        $this->pdo = Database::getConnection();
    }

    // Crear publicación
    public function crear($id_usuario, $titulo, $descripcion, $imagen = null)
    {
        $stmt = $this->pdo->prepare(
            "INSERT INTO publicaciones (id_usuario, titulo, descripcion, imagen, creacion) VALUES (?, ?, ?, ?, NOW())"
        );
        return $stmt->execute([$id_usuario, $titulo, $descripcion, $imagen]);
    }

    // Obtener publicaciones de un usuario
    public function getPorUsuario($id_usuario)
    {
        $stmt = $this->pdo->prepare("SELECT * FROM publicaciones WHERE id_usuario = ? ORDER BY creacion DESC");
        $stmt->execute([$id_usuario]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // Obtener publicación por id
    public function getById($id)
    {
        $stmt = $this->pdo->prepare("SELECT * FROM publicaciones WHERE id = ?");
        $stmt->execute([$id]);
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }
}
