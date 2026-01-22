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
using wikz_escritorio.Properties;

namespace wikz_escritorio
{
    public partial class Principal : Form
    {
        List<Publicacion> publicaciones = new List<Publicacion>();
        List<Coleccion> colecciones = new List<Coleccion>();

        string rutaPublicaciones = "C:\\Users\\Usuario\\Desktop\\Wikz!\\Recursos\\archivosPrograma\\publicaciones.txt";
        string rutaColecciones = "C:\\Users\\Usuario\\Desktop\\Wikz!\\Recursos\\archivosPrograma\\colecciones.txt";

        int anchoCeldaColeccion = 0;

        public Principal()
        {
            InitializeComponent();

            cargarPublicaciones();
            cargarColecciones();

            //MessageBox.Show($"cant publicaciones {publicaciones.Count}, colecciones {colecciones.Count}");
        }

        public void cargarPublicaciones()
        {
            publicaciones.Clear();
            floPublicaciones.Controls.Clear();

            floPublicaciones.SuspendLayout();

            if (File.Exists(rutaPublicaciones))
            {
                try
                {
                    using (StreamReader sr = new StreamReader(rutaPublicaciones))
                    {
                        string linea = sr.ReadLine();

                        while (linea != null)
                        {

                            string[] p = linea.Split(',');

                            string nombre = p[0];

                            if (!int.TryParse(p[1], out int id))
                            {
                                throw new ArgumentException("parametro equivocado en la id");
                            }

                            Image imagen = Resources.colegas;


                            string descripcion = p[3];

                            Publicacion pub = new Publicacion(nombre, id, imagen, descripcion);
                            publicaciones.Add(pub);

                            linea = sr.ReadLine();
                        }
                    }
                }
                catch (IOException)
                {
                    Console.WriteLine("Error en el archivo");
                }
            }

            //Añadir las publicaciones al layout
            foreach (Publicacion p in publicaciones)
            {
                PictureBox pbPub = new PictureBox();

                pbPub.Image = p.FotoPublicacion;

                pbPub.Dock = DockStyle.Top;
                pbPub.Height = (int)(floPublicaciones.Width * 0.4);
                pbPub.Width = (int)(floPublicaciones.Width * 0.20);

                pbPub.SizeMode = PictureBoxSizeMode.Zoom;
                pbPub.BackColor = Color.Black;
                pbPub.Cursor = Cursors.Hand;

                int margin = (int)(floPublicaciones.Width * 0.022);
                pbPub.Margin = new Padding(margin, margin/2, margin, margin/2);    // left, top, right, bottom


                //Ajustar la altura en funcion del tamaño de la ventana
                floPublicaciones.Resize += (s, e) =>
                {
                    pbPub.Height = (int)(floPublicaciones.Width * 0.6);
                    pbPub.Invalidate();
                };

                //Decoracion del Picture Box
                pbPub.Paint += (s, e) =>
                {
                    var radius = 12;
                    var rect = pbPub.ClientRectangle;
                    using (var path = new System.Drawing.Drawing2D.GraphicsPath())
                    {
                        path.AddArc(rect.X, rect.Y, radius, radius, 180, 90);
                        path.AddArc(rect.Right - radius, rect.Y, radius, radius, 270, 90);
                        path.AddArc(rect.Right - radius, rect.Bottom - radius, radius, radius, 0, 90);
                        path.AddArc(rect.X, rect.Bottom - radius, radius, radius, 90, 90);
                        path.CloseFigure();
                        pbPub.Region = new Region(path);
                    }
                };

                //Cargar las imagenes
                floPublicaciones.Controls.Add(pbPub);
            }

            floPublicaciones.ResumeLayout();
        }


        public void cargarColecciones()
        {
            colecciones.Clear();
            floColecciones.Controls.Clear();
            floColecciones.SuspendLayout();

            if (File.Exists(rutaColecciones))
            {
                try
                {
                    using (StreamReader sr = new StreamReader(rutaColecciones))
                    {
                        string linea = sr.ReadLine();

                        while (linea != null)
                        {

                            string[] p = linea.Split(',');

                            string nombre = p[0];

                            if (!int.TryParse(p[1], out int id))
                            {
                                throw new ArgumentException("parametro equivocado en la id");
                            }

                            if (!int.TryParse(p[2], out int elementos))
                            {
                                throw new ArgumentException("parametro equivocado en los elementos");
                            }

                            Image imagen = Resources.evanescense;

                            Coleccion c = new Coleccion(nombre, id, elementos, imagen);
                            colecciones.Add(c);

                            linea = sr.ReadLine();
                        }
                    }
                }
                catch (IOException)
                {
                    Console.WriteLine("Error en el archivo");
                }
            }

            anchoCeldaColeccion = (floColecciones.ClientSize.Width - (1 + 1) * 10) / 1;

            //Añadir el RecyclerView;
            foreach (Coleccion c in colecciones)
            {
                RecyclerView rv = new RecyclerView(c);
                rv.AjustarTamaño(anchoCeldaColeccion);

                floColecciones.Controls.Add(rv);
            }

            floColecciones.SizeChanged += (s, e) =>
            {
                foreach (Control ctrl in floColecciones.Controls)
                {
                    if (ctrl is RecyclerView rv)
                    {
                        rv.Width = floColecciones.ClientSize.Width - 20;
                    }
                }
            };

            floPublicaciones.ResumeLayout();
        }

        public void ajustarTamañoRecycler(Object sender, EventArgs e)
        {
            int columnas = 1;
            int espacio = 10;

            anchoCeldaColeccion = (floColecciones.ClientSize.Width - (columnas + 1) * espacio) / columnas;

            foreach (RecyclerView rv in floColecciones.Controls.OfType<RecyclerView>())
            {
                rv.AjustarTamaño(anchoCeldaColeccion);
            }
        }
    }
}
