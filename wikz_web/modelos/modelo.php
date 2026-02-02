<?php
class Empleado
{

    private $empleado; //Array con los datos de los empleados
    private $db; //acceso a la base de datos

    function __construct()
    {
        $this->empleado = [];
        $this->db = new PDO("mysql:host=localhost;dbname=ejemplo_mvc;charset=utf8", "root", ""); //--> Acceso a la base de datos, ultimo parametro es la password
    }

    public function setEmpleado($nombre, $apellido, $telefono, $departamento, $imagen)
    {

        //Insertar los datos por medio de la consulta
        $sql = "INSERT INTO empleados(nombre, apellidos, telefono, departamentos) VALUES ('$nombre', '$apellido', '$telefono', '$departamento', '$imagen')";

        //Actualizar la base de datos con una variable booleana (result)
        $result = $this->db->query($sql);

        //Cerrar la conexion con la base de datos
        $this->db = null;

        //Lo ultimo siempre es devolver el resultado de forma booleana
        return $result;
    }

    public function getEmpleado()
    {

        $sql = "SELECT * FROM empleados";

        $result = $this->db->query($sql);

        //recoger todos los datos del array como array associativo -> FETCH_ASSOC
        $this->empleado = $result->fetchAll(PDO::FETCH_ASSOC);

        //cerrar la conexion
        $this->db = null;

        return $this->empleado;
    }

    public function editar($id)
    {

        $sql = "SELECT nombre, apellidos, telefono, departamentos FROM empleados WHERE id = {$id}";

        $result = $this->db->query($sql);

        $dato = $result->fetchAll(PDO::FETCH_ASSOC);

        $this->db = null;

        return $dato;
    }

    public function borrar($id)
    {

        $sql = "DELETE FROM empleados WHERE id = {$id}";

        $result = $this->db->query($sql);

        return $result;
    }

    public function actualizar($id, $nombre, $apellido, $telefono, $departamento)
    {

        $sql = "UPDATE FROM empleados set nombre = '$nombre', apellidos = '$apellido', telefono = '$telefono,' departamentos = '$departamento' WHERE id = {$id}";

        $result = $this->db->query($sql);

        $this->db = null;

        return $result;
    }
}
?>
