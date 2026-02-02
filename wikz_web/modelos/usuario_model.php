<?php
require_once "services/ApiClient.php";

class usuario_model
{
    private ApiClient $api;

    public function __construct()
    {
        $this->api = new ApiClient();
    }

    public function registrar(array $usuario)
    {
        return $this->api->post("/addUsuario", $usuario);
    }

    public function obtenerPorId(int $id)
    {
        return $this->api->get("/getUsuarioId", ["id" => $id]);
    }

    public function login(string $nombre, string $pass)
    {
        return $this->api->get("/getUsuarioNombrePass", [
            "nombreUs" => $nombre,
            "passUs" => $pass
        ]);
    }

    public function obtenerTodos()
    {
        return $this->api->get("/getUsuarios");
    }
}