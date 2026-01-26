using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;

namespace wikz_escritorio
{
    public class Usuario
    {
        private string nombre;
        public string Nombre { set; get; }

        private string pass;
        public string Pass { set; get; }

        private string correo;
        public string Correo { set; get; }

        private DateTime fechaCreacion;
        public DateTime FechaCreacion { set; get; }

        private string descripcion;
        public string Descripcion { set; get; }

        private Image fotoPerfil;
        public Image FotoPerfil { set; get; }

        public Usuario(string nombre, string pass, string correo, string descripcion, Image fotoPerfil)
        {
            Nombre = nombre;
            Pass = pass;
            Correo = correo;
            FechaCreacion = DateTime.Now;
            Descripcion = descripcion;
            FotoPerfil = fotoPerfil;
        }

        public Usuario():this("", "", "", "", null) {}
    }
}
