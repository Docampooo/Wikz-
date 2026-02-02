<?php

    if(isset($_GET['id']) && is_numeric($_GET['id'])){ //clave asociativa enviada desde vista --> id

        require_once '../modelos/modelo.php';

        $empleado = new Empleado();
        $dato = $empleado->editar($_GET['id']);

        require_once '../vistas/vista_editar.php';
    } 

?>