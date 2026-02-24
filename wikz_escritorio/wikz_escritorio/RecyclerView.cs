using System;
using System.Drawing;
using System.Windows.Forms;
using wikz_escritorio.Modelos;
using Wikz.Services; // Donde tengas tu clase Api

namespace wikz_escritorio
{
    public partial class RecyclerView : UserControl
    {
        private Publicacion publicacion;
        private Api api = new Api();

        // Colores Wikz
        Color moradoLogo = Color.FromArgb(163, 73, 164);
        Color fondoTarjeta = Color.FromArgb(26, 0, 43); // El morado oscuro de tu móvil #1A002B

        public RecyclerView(Publicacion p)
        {
            InitializeComponent();
            this.publicacion = p;

            ConfigurarDiseno();
            CargarDatos();

            // Eventos de clic vinculados a la imagen, el label y el fondo
            pbImagen.Click += (s, e) => verDatosPublicacion();
            lblNombre.Click += (s, e) => verDatosPublicacion();
            this.Click += (s, e) => verDatosPublicacion();
        }

        private void verDatosPublicacion()
        {
            // Ahora mostrará el ID de autor que arreglamos con el JsonProperty
            MessageBox.Show($"Publicación: {publicacion.Titulo}\n" +
                            $"Autor ID: {publicacion.IdUsuario}\n" +
                            $"Fecha: {publicacion.FechaCreacion}", "Detalles de Wikz");
        }

        private void ConfigurarDiseno()
        {
            // 1. Estilo General de la Tarjeta
            this.BackColor = fondoTarjeta;
            this.Padding = new Padding(8);
            this.Cursor = Cursors.Hand;

            // 2. Configurar el Título (Centrado absoluto)
            lblNombre.Dock = DockStyle.Bottom;
            lblNombre.AutoSize = false; // Importante para que TextAlign funcione
            lblNombre.Height = 40;
            lblNombre.ForeColor = Color.FromArgb(224, 179, 255); // #E0B3FF (Morado clarito)
            lblNombre.Font = new Font("Segoe UI", 11, FontStyle.Bold);
            lblNombre.TextAlign = ContentAlignment.MiddleCenter; // Centrado horizontal y vertical
            lblNombre.BackColor = Color.Transparent;

            // 3. Configurar la Imagen
            pbImagen.Dock = DockStyle.Fill;
            pbImagen.SizeMode = PictureBoxSizeMode.Zoom;
            pbImagen.BackColor = Color.Black;

            // 4. Redondear bordes de la imagen
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

            // Efecto Hover (Cambio de color al pasar el ratón)
            this.MouseEnter += (s, e) => { this.BackColor = Color.FromArgb(45, 10, 60); };
            this.MouseLeave += (s, e) => { this.BackColor = fondoTarjeta; };
        }

        private async void CargarDatos()
        {
            lblNombre.Text = establecerTitulo(publicacion.Titulo.Trim());

            // --- FORMATEO DE FECHA --- // Falla
            try
            {
                if (!string.IsNullOrEmpty(publicacion.FechaCreacion))
                {
                    DateTime dt = DateTime.Parse(publicacion.FechaCreacion);

                    string fechaFormateada = dt.ToString("yyyy-MM-dd HH:mm:ss");

                    publicacion.FechaCreacion = fechaFormateada;
                }
            }
            catch
            {
                publicacion.FechaCreacion = "Fecha no válida";
            }

            // Carga de la foto
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