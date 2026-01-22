using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace wikz_escritorio
{
    public partial class Principal : Form
    {
        List<Publicacion> publicaciones = new List<Publicacion>();

        string rutaPublicaciones = "C:\\Users\\Usuario\\Desktop\\Wikz!\\Recursos\\archivosPrograma\\publicaciones.txt";


        public Principal()
        {
            InitializeComponent();
        }

        public void cargarPublicaciones()
        {
            floPublicaciones.Controls.Clear();
            floPublicaciones.SuspendLayout();

            if (File.Exists(rutaPublicaciones))
            {
                try
                {
                    using(StreamReader sr = new StreamReader(rutaPublicaciones))
                    {
                        string linea = "";

                        while(linea != null)
                        {
                            linea = sr.ReadLine();

                            string[] dato = linea.Split(':');
                            foreach(string s in dato)
                            {
                                string[] p = s.Split(',');

                                string nombre = p[0];

                                if (!int.TryParse(p[1], out int id))
                                {
                                    throw new ArgumentException("parametro equivocado en la id");
                                }

                                Image imagen = null;

                                string descripcion = p[3];

                                Publicacion pub = new Publicacion(nombre, id, imagen, descripcion);
                                publicaciones.Add(pub);
                            }
                        }
                    }
                }
                catch (IOException)
                {
                    Console.WriteLine("Error en el archivo");
                }
            }

            foreach(Publicacion p in publicaciones)
            {
                RecyclerView rv = new RecyclerView(p);
                rv.Width = floPublicaciones.ClientSize.Width - 10;

                floPublicaciones.Controls.Add(rv);
            }

            floPublicaciones.ResumeLayout();
        }
    }
}
