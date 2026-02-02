<?php

class Coleccion
{
    private int $id;
    private int $idUsuario;
    private string $titulo;
    private array $elementos; // Array de Publicacion
    private ?string $imagenBase64;

    // Constructor principal
    public function __construct(
        int $idUsuario = 0,
        string $titulo = "",
        array $elementos = [],
        ?string $imagenBase64 = null
    ) {
        $this->idUsuario = $idUsuario;
        $this->titulo = $titulo;
        $this->elementos = $elementos;
        $this->imagenBase64 = $imagenBase64;
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
        return $this->idUsuario;
    }

    public function setIdUsuario(int $idUsuario): void
    {
        $this->idUsuario = $idUsuario;
    }

    public function getTitulo(): string
    {
        return $this->titulo;
    }

    public function setTitulo(string $titulo): void
    {
        $this->titulo = $titulo;
    }

    /**
     * @return Publicacion[]
     */
    public function getElementos(): array
    {
        return $this->elementos;
    }

    /**
     * @param Publicacion[] $elementos
     */
    public function setElementos(array $elementos): void
    {
        $this->elementos = $elementos;
    }

    public function getImagenBase64(): ?string
    {
        return $this->imagenBase64;
    }

    public function setImagenBase64(?string $imagenBase64): void
    {
        $this->imagenBase64 = $imagenBase64;
    }

    // ===== toString =====
    public function __toString(): string
    {
        return "Coleccion{id={$this->id}, titulo={$this->titulo}, elementos=" .
            count($this->elementos) .
            ", idUsuario={$this->idUsuario}}";
    }
}
