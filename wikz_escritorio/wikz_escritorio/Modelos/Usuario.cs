using System;

namespace wikz_escritorio.Modelos
{
    public class Usuario
    {
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Email { get; set; }
        public string Biografia { get; set; }
        public string FotoPerfilBase64 { get; set; }
        public string FechaCreacion { get; set; }

        // Constructor vacío (necesario para la deserialización de la API)
        public Usuario()
        {
        }

        // Constructor con parámetros
        public Usuario(string nombre, string correo, string biografia, string fotoPerfil, string fechaCreacion = null)
        {
            Nombre = nombre;
            Email = correo;
            Biografia = biografia;
            FotoPerfilBase64 = fotoPerfil;
            // Si fechaCreacion es nulo, usamos la fecha actual
            FechaCreacion = fechaCreacion;
        }
        public override string ToString()
        {
            return $"Usuario{{nombre={Nombre}, email={Email}}}";
        }
    }
}