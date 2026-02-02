<?php

class Publicacion
{
    private int $id;
    private int $id_usuario;
    private string $titulo;
    private string $descripcion;
    private ?string $imagenBase64;
    private DateTime $fechaCreacion;

    // Constructor principal
    public function __construct(
        int $id_usuario = 0,
        string $titulo = "",
        ?string $imagenBase64 = null,
        string $descripcion = "",
        ?DateTime $fechaCreacion = null
    ) {
        $this->id_usuario = $id_usuario;
        $this->titulo = $titulo ?? "";
        $this->imagenBase64 = $imagenBase64;
        $this->descripcion = $descripcion ?? "";
        $this->fechaCreacion = $fechaCreacion ?? new DateTime();
    }

    // ===== GETTERS & SETTERS =====

    public function getId(): int
    {
        return $this->id;
    }

    public function setId(int $id): void
    {
        $this->id = $id;
    }

    public function getIdUsuario(): int
    {
        return $this->id_usuario;
    }

    public function setIdUsuario(int $id_usuario): void
    {
        $this->id_usuario = $id_usuario;
    }

    public function getTitulo(): string
    {
        return $this->titulo;
    }

    public function setTitulo(string $titulo): void
    {
        $this->titulo = $titulo;
    }

    public function getDescripcion(): string
    {
        return $this->descripcion;
    }

    public function setDescripcion(string $descripcion): void
    {
        $this->descripcion = $descripcion;
    }

    public function getImagenBase64(): ?string
    {
        return $this->imagenBase64;
    }

    public function setImagenBase64(?string $imagenBase64): void
    {
        $this->imagenBase64 = $imagenBase64;
    }

    public function getFechaCreacion(): DateTime
    {
        return $this->fechaCreacion;
    }

    public function setFechaCreacion(DateTime $fechaCreacion): void
    {
        $this->fechaCreacion = $fechaCreacion;
    }

    // ===== toString =====
    public function __toString(): string
    {
        return "Publicacion{id={$this->id}, id_usuario={$this->id_usuario}, titulo={$this->titulo}}";
    }
}