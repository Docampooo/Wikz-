<?php
// models/Usuario.php
require_once 'config/Database.php';

class Usuario
{
    private $pdo;

    public $id;
    public $nombre;
    public $email;
    public $pass;
    public $bio;
    public $foto_perfil;
    public $fecha_creacion;

    public function __construct()
    {
        $this->pdo = Database::getConnection();
    }

    // Obtener usuario por email
    public function getByEmail($email)
    {
        $stmt = $this->pdo->prepare("SELECT id, nombre, email, bio, foto_perfil, creacion FROM usuarios WHERE email = ?");
        $stmt->execute([$email]);
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    // Crear nuevo usuario
    public function crear($nombre, $email, $pass, $bio = '', $foto = null)
    {
        $hash = password_hash($pass, PASSWORD_BCRYPT);
        $stmt = $this->pdo->prepare(
            "INSERT INTO usuarios (nombre, email, pass, bio, foto_perfil, creacion) VALUES (?, ?, ?, ?, ?, NOW())"
        );
        return $stmt->execute([$nombre, $email, $hash, $bio, $foto]);
    }

    // Obtener usuario por id
    public function getById($id)
    {
        $stmt = $this->pdo->prepare("SELECT id, nombre, email, bio, foto_perfil, creacion FROM usuarios WHERE id = ?");
        $stmt->execute([$id]);
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }
}
