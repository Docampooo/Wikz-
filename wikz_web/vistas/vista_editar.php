<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar datos del usuario</title>
</head>
<body>
    <h1>Editar datos de usuario</h1>

    <form action="../controladores/controlador_actualizar.php" method="post" enctype="multipart/form-data">

        <input type="text" name="nombre" id="nombre" value="<?php echo $dato[0]["nombre"]?>">

        <input type="text" name="apellidos" id="apellidos" value="<?php echo $dato[0]["apellidos"]?>">

        <input type="tel" name="telefono" id="telefono" value="<?php echo $dato[0]["telefono"]?>">

        <input type="text" name="departamento" id="departamento" value="<?php echo $dato[0]["departamentos"]?>">
        
        <input type="file" name="imagen" id="imagen"><br>
        
        <input type="hidden" name="id" id= "id" value="<?php echo $_GET['id']?>">

        <input type="submit" value="Actualizar">
    </form>
</body>
</html>