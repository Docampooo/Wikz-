using System;
using System.Drawing;
using System.Threading.Tasks;
using System.Windows.Forms;
using wikz_escritorio.Modelos; 
using Wikz.Services;
using System.Net.Http.Headers;

namespace wikz_escritorio
{
    public partial class Registro : Form
    {
        private Api api = new Api();

        Color moradoLogo = Color.FromArgb(163, 73, 164);
        Color grisClaro = Color.FromArgb(122, 122, 122);

        public Registro()
        {
            InitializeComponent();
        }

        public void MostrarToast(string mensaje)
        {
            Label toast = new Label();
            toast.Text = mensaje;
            toast.AutoSize = false;
            toast.Size = new Size(300, 60);
            toast.BackColor = moradoLogo;
            toast.ForeColor = Color.White;
            toast.TextAlign = ContentAlignment.MiddleCenter;
            toast.Font = new Font("Segoe UI", 10, FontStyle.Bold);

            // Centrado dinámico
            toast.Location = new Point(
                (this.ClientSize.Width - toast.Width) / 2,
                (this.ClientSize.Height - toast.Height) / 2
            );

            this.Controls.Add(toast);
            toast.BringToFront();

            Timer t = new Timer();
            t.Interval = 2000;
            t.Tick += (s, e) =>
            {
                t.Stop();
                this.Controls.Remove(toast);
                toast.Dispose();
                t.Dispose();
            };
            t.Start();
        }

        // 1. Cambiamos a async para usar la API
        private async void btnLogIn_Click(object sender, EventArgs e)
        {
            string nombre = txtRegistroNombre.Text.Trim();
            string pass = txtRegistroPass.Text.Trim();

            if (string.IsNullOrEmpty(nombre))
            {
                MostrarToast("El campo del nombre ha de estar cubierto");
                return;
            }

            if (string.IsNullOrEmpty(pass))
            {
                MostrarToast("El campo de la contraseña ha de estar cubierto");
                return;
            }

            // Verificación con la API de Java
            Usuario usuarioEncontrado = await api.GetUsuarioNombrePassAsync(nombre, pass);

            if (usuarioEncontrado != null)
            {
                // Éxito: Enviar a la pantalla principal pasando el usuario
                Principal p = new Principal(usuarioEncontrado);
                p.Show();
                this.Hide();
            }
            else
            {
                MostrarToast("No se ha encontrado el usuario");
            }
        }

        private void btnSignUp_Click(object sender, EventArgs e)
        {
            Registrar_usuario ru = new Registrar_usuario(this);
            ru.Show();
            this.Hide();
        }
    }
}