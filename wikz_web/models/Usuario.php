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
}
