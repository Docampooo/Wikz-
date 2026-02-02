<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de empleados</title>

    <style>
        th{
            width: 150px;
        }

        td{
            text-align: center;
        }
    </style>
</head>

<body>
    <h1>Listado de los empleados</h1>
    <table>
        <tr>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Telefono</th>
            <th>Departamento</th>
            <th>Foto</th>
        </tr>

        <?php
        for ($i = 0; $i < count($result); $i++) {
        ?>
            <tr>
                <td><?php echo $result[$i]['nombre'] ?></td>
                <td><?php echo $result[$i]['apellidos'] ?></td>
                <td><?php echo $result[$i]['telefono'] ?></td>
                <td><?php echo $result[$i]['departamentos'] ?></td>
                <td><img src="../<?php echo $result[$i]['imagen']?>" alt=""></td>

                <td><?php echo "<a href=\"../controladores/controlador_editar.php?id={$result[$i]['id']}\">Actualizar</a>" ?></td>
                <td><?php echo "<a href=\"../controladores/controlador_borrar.php?id={$result[$i]['id']}\">Borrar</a>" ?></td>

            </tr>
        <?php } ?>
    </table>

    <a href="../index.php">Pagina inicio</a>
</body>

</html>