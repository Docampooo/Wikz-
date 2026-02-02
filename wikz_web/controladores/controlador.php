<?php

// crea una copia de este archivo y es accesible desde este archivo

    require_once '../modelos/modelo.php';

    $empleado = new Empleado();

    $result = $empleado->getEmpleado(); //Array de arrays

    //Enviar las variables a vista con este require_once
    require_once '../vistas/vista.php';
?>