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
        Color grisClaro = Color.FromArgb(122, 122, 122);
        public RecyclerView(Coleccion c)
        {
            InitializeComponent();

            //modificar tamaño
            this.AutoSize = true;
            this.AutoSizeMode = AutoSizeMode.GrowAndShrink;

            this.Anchor = AnchorStyles.Top | AnchorStyles.Left;

            this.BackColor = Color.FromArgb(30, 30, 30);
            this.Padding = new Padding(8);

            pbImagen.Dock = DockStyle.Top;
            pbImagen.Height = (int)(this.Width * 0.6);

            pbImagen.SizeMode = PictureBoxSizeMode.Zoom;
            pbImagen.BackColor = Color.Black;
            pbImagen.Cursor = Cursors.Hand;

            //Ajustar la altura en funcion del tamaño de la ventana
            this.Resize += (s, e) =>
            {
                pbImagen.Height = (int)(this.Width * 0.6);
                pbImagen.Invalidate();
            };

            //Decoracion del Picture Box
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

            //Decoracion del Label
            lblNombre.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            lblNombre.ForeColor = moradoLogo;
            lblNombre.Dock = DockStyle.Top;

            pbImagen.Image = c.FotoColeccion;
            lblNombre.Text = c.Nombre;
        }

        public void AjustarTamaño(int anchoDisponible)
        {
            this.Width = anchoDisponible;
            this.Height = (int)(anchoDisponible * 1.3);
        }
    }
}
