<?php 

    
    if ((isset($_POST["nombre"])) && ($_POST["nombre"] != "") && (isset($_POST["apellidos"])) && ($_POST["apellidos"] != "") && (isset($_POST["telefono"])) && ($_POST["telefono"] != "") && (isset($_POST["departamento"])) && ($_POST["departamento"] != "")) {

        //llamar a modelo.php
        require_once '../modelos/modelo.php';

        //crear variable con un objeto empleado
        $empleado = new Empleado();

        $result = $empleado->borrar($_GET['id']);

        if ($result == true) {

            echo "Se ha borrado el empleado con id: " . $id;
        } else {

            echo "No se ha podido actualizar el empleado";
        }

    }

?>