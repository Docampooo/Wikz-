using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace wikz_escritorio
{
    public partial class RecyclerView : UserControl
    {

        Color moradoLogo = Color.FromArgb(163, 73, 164);
        public RecyclerView(Coleccion c)
        {
            InitializeComponent();

            //modificar tamaño
            this.BackColor = Color.FromArgb(30, 30, 30);

            pbImagen.Dock = DockStyle.Top;
            pbImagen.SizeMode = PictureBoxSizeMode.Zoom;
            pbImagen.BackColor = Color.Black;

            pbImagen.Width = (int)(this.Width * 0.35);
            pbImagen.Height = (int)(this.Height * 0.33);

            pbImagen.Cursor = Cursors.Hand;

            //Decoracion del Picture Box, celda con bordes
            pbImagen.Paint += (s, e) =>
            {
                var radius = 12;
                var rect = pbImagen.ClientRectangle;
                using (var path = new System.Drawing.Drawing2D.GraphicsPath())
                {
                    path.AddArc(rect.X, rect.Y, radius, radius, 180, 90);
                    path.AddArc(rect.Right - radius, rect.Y, radius, radius, 270, 90);
                    path.AddArc(rect.Right - radius, rect.Bottom - radius, radius, radius, 0, 90);
                    path.AddArc(rect.X, rect.Bottom - radius, radius, radius, 90, 90);
                    path.CloseFigure();
                    pbImagen.Region = new Region(path);
                }
            };

            pbImagen.Image = c.FotoColeccion;
            lblNombre.Text = establecerTitulo(c.Nombre);
        }

        public string establecerTitulo(string cad)
        {

            if (cad.Length >= 10)
            {
                return $"{cad.Substring(0, 7)}...";
            }
            else
            {
                return cad;
            }
        }

        private void RecyclerView_Click(object sender, EventArgs e)
        {

        }
    }
}
