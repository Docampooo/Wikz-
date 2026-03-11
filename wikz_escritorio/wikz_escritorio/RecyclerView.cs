using System;
using System.Drawing;
using System.Windows.Forms;
using wikz_escritorio.Modelos;
using Wikz.Services;

namespace wikz_escritorio
{
    public partial class RecyclerView : UserControl
    {
        private Publicacion publicacion;
        private Usuario uSesion; 

        private Api api = new Api();

        // Colores Wikz
        Color moradoLogo = Color.FromArgb(163, 73, 164);
        Color fondoTarjeta = Color.FromArgb(26, 0, 43);

        public RecyclerView(Publicacion p, Usuario u)
        {
            InitializeComponent();
            this.publicacion = p;
            this.uSesion = u;

            ConfigurarDiseno();
            CargarDatos();

            // Eventos de clic
            pbImagen.Click += (s, e) => verDatosPublicacion();
            lblNombre.Click += (s, e) => verDatosPublicacion();
            this.Click += (s, e) => verDatosPublicacion();
        }

        private void verDatosPublicacion()
        {
            // Abrir la vista de detalle
            VerPublicacion v = new VerPublicacion(publicacion, uSesion);
            v.ShowDialog();
        }

        private void ConfigurarDiseno()
        {
            this.BackColor = fondoTarjeta;
            this.Padding = new Padding(8);
            this.Cursor = Cursors.Hand;

            lblNombre.Dock = DockStyle.Bottom;
            lblNombre.AutoSize = false;
            lblNombre.Height = 40;
            lblNombre.ForeColor = Color.FromArgb(224, 179, 255);
            lblNombre.Font = new Font("Segoe UI", 11, FontStyle.Bold);
            lblNombre.TextAlign = ContentAlignment.MiddleCenter;
            lblNombre.BackColor = Color.Transparent;

            pbImagen.Dock = DockStyle.Fill;
            pbImagen.SizeMode = PictureBoxSizeMode.Zoom;
            pbImagen.BackColor = Color.Black;

            pbImagen.Paint += (s, e) =>
            {
                var radius = 25;
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

            this.MouseEnter += (s, e) => { this.BackColor = Color.FromArgb(45, 10, 60); };
            this.MouseLeave += (s, e) => { this.BackColor = fondoTarjeta; };
        }

        private async void CargarDatos()
        {
            lblNombre.Text = establecerTitulo(publicacion.Titulo.Trim());

            try
            {
                if (!string.IsNullOrEmpty(publicacion.FechaCreacion))
                {
                    DateTime dt = DateTime.Parse(publicacion.FechaCreacion);
                    publicacion.FechaCreacion = dt.ToString("yyyy-MM-dd HH:mm:ss");
                }
            }
            catch
            {
                publicacion.FechaCreacion = "Fecha no válida";
            }

            Image img = await api.GetFotoPublicacionAsync(publicacion.Id);
            pbImagen.Image = img ?? Properties.Resources.cora;
        }

        public string establecerTitulo(string cad)
        {
            if (string.IsNullOrEmpty(cad)) return "";
            return cad.Length > 18 ? cad.Substring(0, 15).Trim() + "..." : cad;
        }
    }
}
