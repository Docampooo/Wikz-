<?php

class UsuarioController
{
    private string $apiBase = "http://localhost:8080/api/wikz/operaciones";

    /* =========================
       REGISTRO (API JAVA)
       ========================= */
    public function registro()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {

            $nombre = trim($_POST['nombre'] ?? '');
            $email  = trim($_POST['email'] ?? '');
            $pass   = trim($_POST['pass'] ?? '');
            $pass2  = trim($_POST['pass2'] ?? '');

            if ($nombre === '' || $email === '' || $pass === '' || $pass2 === '') {
                $error = "Todos los campos son obligatorios";
                require 'views/usuario/registro.php';
                return;
            }

            if ($pass !== $pass2) {
                $error = "Las contraseñas no coinciden";
                require 'views/usuario/registro.php';
                return;
            }

            $payload = [
                "nombre" => $nombre,
                "email"  => $email,
                "pass"   => $pass,
                "biografia" => "",
                "fotoPerfilBase64" => null
            ];

            $context = stream_context_create([
                'http' => [
                    'method'  => 'POST',
                    'header'  => "Content-Type: application/json\r\n",
                    'content' => json_encode($payload),
                    'ignore_errors' => true
                ]
            ]);

            $response = @file_get_contents($this->apiBase . "/addUsuario", false, $context);

            if ($response === false || !isset($http_response_header)) {
                $error = "No se puede conectar con el servidor de usuarios";
                require 'views/usuario/registro.php';
                return;
            }

            $status   = $this->getHttpStatus($http_response_header);

            if ($status === 200) {
                return $this->procesarLoginAutomatico($nombre, $pass);
            }

            if ($status === 409) {
                $error = "Ya existe un usuario con ese nombre o correo";
            } elseif ($status === 400) {
                $error = "Datos de usuario incompletos";
            } else {
                $error = "Error al registrar usuario";
            }

            require 'views/usuario/registro.php';
            return;
        }

        require 'views/usuario/registro.php';
    }

    private function procesarLoginAutomatico($nombreUs, $passUs)
    {
        $url = $this->apiBase . "/getUsuarioNombrePass?"
            . "nombreUs=" . urlencode($nombreUs)
            . "&passUs=" . urlencode($passUs);

        $json = @file_get_contents($url, false, stream_context_create([
            'http' => ['ignore_errors' => true]
        ]));

        $status = $this->getHttpStatus($http_response_header ?? []);
        $user = json_decode($json, true);

        if ($status === 200 && isset($user['id']) && $user['id'] > 0) {
            if (session_status() === PHP_SESSION_NONE) session_start();
            $_SESSION['usuario'] = $user;
            header("Location: index.php?controller=Usuario&action=main");
            exit;
        } else {
            return "Usuario o contraseña incorrectos";
        }
    }

    /* =========================
       LOGIN (API JAVA)
       ========================= */
    public function login()
    {
        $error = null;

        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $nombreUs = $_POST['nombreUs'] ?? '';
            $passUs   = $_POST['passUs'] ?? '';

            $resultado = $this->procesarLoginAutomatico($nombreUs, $passUs);

            if (is_string($resultado)) {
                $error = $resultado;
            }
        }

        require 'views/usuario/login.php';
    }

    /* =========================
       PANTALLA PRINCIPAL
       ========================= */
    public function main()
    {
        if (session_status() === PHP_SESSION_NONE) session_start();

        // Verificación de sesión
        if (!isset($_SESSION['usuario']) || $_SESSION['usuario']['id'] <= 0) {
            header("Location: index.php?controller=Usuario&action=login");
            exit;
        }

        $usuarioActivo = $_SESSION['usuario'];

        // Cargamos la vista principal que contiene el esqueleto de la SPA
        require 'views/usuario/principal.php';
    }

    /* =========================
       UTILS & AJAX
       ========================= */
    public function actualizarSesionJS()
    {
        if (session_status() === PHP_SESSION_NONE) session_start();
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $_SESSION['usuario']['nombre'] = $_POST['nombre'] ?? $_SESSION['usuario']['nombre'];
            $_SESSION['usuario']['biografia'] = $_POST['biografia'] ?? $_SESSION['usuario']['biografia'];
            echo "ok";
        }
        exit;
    }

    private function getHttpStatus(array $headers): int
    {
        if (!isset($headers[0])) return 500;
        preg_match('{HTTP\/\S*\s(\d{3})}', $headers[0], $match);
        return (int)($match[1] ?? 500);
    }
}