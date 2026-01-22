using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace wikz_escritorio
{
    public class Coleccion
    {
        private string nombre;
        public string Nombre { set; get; }

        private int id;
        public int Id { set; get; }

        private int elementos;
        public int Elementos { set; get; }

        private Image fotoColeccion;
        public Image FotoColeccion { set; get; }

        public Coleccion(string nombre, int id, int elementos , Image fotoColeccion)
        {
            Nombre = nombre;
            Id = id;
            Elementos = elementos;
            FotoColeccion = fotoColeccion;
        }

        public Coleccion() : this("", 0, 0, null) { }
    }
}
