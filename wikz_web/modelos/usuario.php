<?php

require_once "config/database.php";

class Usuario
{
    private ?int $id = null;
    private string $nombre;
    private string $pass;
    private string $email;
    private DateTime $fechaCreacion;
    private string $biografia;
    private ?string $fotoPerfilBase64;

    private PDO $db;

    // ===== CONSTRUCTOR =====
    public function __construct(
        string $nombre = "",
        string $correo = "",
        string $pass = "",
        string $biografia = "",
        ?string $fotoPerfil = null,
        ?DateTime $fechaCreacion = null
    ) {
        $this->db = Database::getConnection();

        $this->nombre = $nombre;
        $this->email = $correo;
        $this->pass = $pass;
        $this->biografia = $biografia;
        $this->fotoPerfilBase64 = $fotoPerfil;
        $this->fechaCreacion = $fechaCreacion ?? new DateTime();
    }

    // ===== CRUD =====

    public function guardar(): bool
    {
        $sql = "INSERT INTO usuarios 
                (nombre, email, pass, biografia, foto_perfil, fecha_creacion)
                VALUES (?, ?, ?, ?, ?, ?)";

        $stmt = $this->db->prepare($sql);

        $resultado = $stmt->execute([
            $this->nombre,
            $this->email,
            password_hash($this->pass, PASSWORD_DEFAULT),
            $this->biografia,
            $this->fotoPerfilBase64,
            $this->fechaCreacion->format('Y-m-d H:i:s')
        ]);

        if ($resultado) {
            $this->id = (int)$this->db->lastInsertId();
        }

        return $resultado;
    }

    public static function obtenerPorId(int $id): ?Usuario
    {
        $db = Database::getConnection();

        $stmt = $db->prepare("SELECT * FROM usuarios WHERE id = ?");
        $stmt->execute([$id]);

        $data = $stmt->fetch(PDO::FETCH_ASSOC);

        if (!$data) {
            return null;
        }

        return self::fromArray($data);
    }

    public static function obtenerTodos(): array
    {
        $db = Database::getConnection();

        $stmt = $db->query("SELECT * FROM usuarios");
        $usuarios = [];

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $usuarios[] = self::fromArray($row);
        }

        return $usuarios;
    }

    // ===== HIDRATACIÓN =====
    private static function fromArray(array $data): Usuario
    {
        $usuario = new Usuario(
            $data['nombre'],
            $data['email'],
            "",
            $data['biografia'],
            $data['foto_perfil'],
            new DateTime($data['fecha_creacion'])
        );

        $usuario->id = (int)$data['id'];
        $usuario->pass = $data['pass']; // hash

        return $usuario;
    }

    // ===== GETTERS =====

    public function getId(): ?int { return $this->id; }
    public function getNombre(): string { return $this->nombre; }
    public function getEmail(): string { return $this->email; }
    public function getBiografia(): string { return $this->biografia; }
    public function getFechaCreacion(): DateTime { return $this->fechaCreacion; }
    public function getFotoPerfilBase64(): ?string { return $this->fotoPerfilBase64; }

    public function verificarPassword(string $pass): bool
    {
        return password_verify($pass, $this->pass);
    }

    public function __toString(): string
    {
        return "Usuario{id={$this->id}, nombre={$this->nombre}, email={$this->email}}";
    }
}