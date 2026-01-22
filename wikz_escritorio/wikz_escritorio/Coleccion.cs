using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace wikz_escritorio
{
    public class Coleccuion
    {
        private string nombre;
        public string Nombre { set; get; }

        private int id;
        public int Id { set; get; }

        private int elementos;
        public int Elementos { set; get; }

        private int fotoColeccion;
        public int FotoColeccion { set; get; }

        public Coleccion(string nombre, int id, int elementos , int fotoColeccion)
        {
            Nombre = nombre;
            Id = id;
            Elementos = elementos;
            FotoColeccion = fotoColeccion;
        }

        public Coleccion() : this("", 0, 0, 0) { }
    }
}
