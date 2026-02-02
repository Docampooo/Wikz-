<?php

class HomeController
{
    public function mostrar()
    {
        if (!isset($_SESSION["usuario"])) {
            header("Location: index.php");
            exit;
        }

        $usuario = $_SESSION["usuario"];
        require "vistas/vista_home.php";
    }
}