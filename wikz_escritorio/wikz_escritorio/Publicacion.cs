using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace wikz_escritorio
{
    public class Publicacion
    {
        private string nombre;
        public string Nombre { set; get; }

        private int id;
        public int Id { set; get; }

        private string descripcion;
        public string Descripcion { set; get; }

        private Image fotoPublicacion;
        public Image FotoPublicacion { set; get; }

        private DateTime fechaCreacion;
        public DateTime FechaCreacion { set; get; }

        private int likes;
        public int Likes { set; get; }
       

        public Publicacion(string nombre, int id, Image fotoPublicacion, string descripcion, int likes)
        {
            Nombre = nombre;
            Id = id;
            FotoPublicacion = fotoPublicacion;
            Descripcion = descripcion;
            FechaCreacion = DateTime.Now;
            Likes = likes;
        }

        public Publicacion() : this("", 0, null, "", 0) { }

        
    }
}
