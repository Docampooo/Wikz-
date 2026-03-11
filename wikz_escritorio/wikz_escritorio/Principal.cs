using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Threading.Tasks;
using System.Windows.Forms;
using wikz_escritorio.Modelos;
using Wikz.Services;

namespace wikz_escritorio
{
    public partial class Principal : Form
    {
        private Api api = new Api();
        private Usuario usuarioSesion;

        public Principal(Usuario u)
        {
            InitializeComponent();
            this.usuarioSesion = u;

            this.StartPosition = FormStartPosition.CenterScreen;
            this.ClientSize = new Size(600, 800);
            this.MinimumSize = new Size(600, 800);
            this.MaximumSize = new Size(600, 800);

            btnExplorar.Location = new Point(10, 10);
            btnPublicar.Location = new Point(pnNavegador.Width / 2 - btnExplorar.Width / 2, 10);
            btnPerfil.Location = new Point(pnNavegador.Width - 10 - btnPerfil.Width, 10);

            CargarMuroExplorar();
        }

        private async Task CargarMuroExplorar()
        {
            floPublicaciones.Controls.Clear();
            floPublicaciones.SuspendLayout();

            try
            {
                var publicaciones = await api.GetPublicacionesAsync();
                foreach (var p in publicaciones)
                {
                    RecyclerView card = new RecyclerView(p, usuarioSesion);

                    // Ajuste dinámico: 2 columnas
                    card.Width = (floPublicaciones.Width / 2) - 25;
                    floPublicaciones.Controls.Add(card);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al cargar: " + ex.Message);
            }

            floPublicaciones.ResumeLayout();
        }

        //Apartado del perfil del usuario
        private async Task CargarMuroUsuario()
        {
            //Limpieza y preparación
            floPublicaciones.Controls.Clear();
            floPublicaciones.SuspendLayout();

            //Configuración de la Cabecera
            CabeceraPerfil cabecera = new CabeceraPerfil(usuarioSesion);
            cabecera.Width = floPublicaciones.Width - 25;
            cabecera.Margin = new Padding(0, 20, 0, 20);
            floPublicaciones.Controls.Add(cabecera);

            try
            {
                //Carga de fotos desde API
                List<Publicacion> misPublis = await api.GetPublicacionesUsuarioAsync(usuarioSesion.Id);

                foreach (Publicacion p in misPublis)
                {
                    RecyclerView item = new RecyclerView(p, usuarioSesion);
                    item.Width = (int)(floPublicaciones.Width * 0.45);
                    item.Height = 220;
                    floPublicaciones.Controls.Add(item);
                }

                // Creación dinámica del botón de Cerrar Sesión
                Button btnLogout = new Button();
                btnLogout.Text = "CERRAR SESIÓN";
                btnLogout.Size = new Size(floPublicaciones.Width - 50, 45); // Casi todo el ancho
                btnLogout.BackColor = Color.FromArgb(150, 0, 0); // Rojo oscuro
                btnLogout.ForeColor = Color.White;
                btnLogout.Font = new Font("Segoe UI", 10, FontStyle.Bold);
                btnLogout.FlatStyle = FlatStyle.Flat;
                btnLogout.FlatAppearance.BorderSize = 0;
                btnLogout.Cursor = Cursors.Hand;

                btnLogout.Margin = new Padding(25, 30, 25, 40);

                // Evento Click para cerrar la sesión
                btnLogout.Click += (s, e) =>
                {
                    var confirmacion = MessageBox.Show("¿Estás seguro de que quieres cerrar sesión?",
                                              "Cerrar Sesión",
                                              MessageBoxButtons.YesNo,
                                              MessageBoxIcon.Question);

                    if (confirmacion == DialogResult.Yes)
                    {
                        Form padre = this.FindForm();

                        Registro r = new Registro();
                        r.Show();

                        // cerrar el formulario Principal
                        padre.Close();
                    }
                };

                floPublicaciones.Controls.Add(btnLogout);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: " + ex.Message);
            }

            floPublicaciones.ResumeLayout();
        }

        public void ActualizarUsuarioSesion(Usuario usuarioActualizado)
        {
            this.usuarioSesion = usuarioActualizado;

            // refrescar el muro de usuario para que se vea la nueva foto/bio inmediatamente
            btnPerfil.PerformClick();
        }

        // --- EVENTO EXPLORAR ---
        private async void btnExplorar_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            await CargarMuroExplorar();
            this.Cursor = Cursors.Default;
        }

        // --- EVENTO PERFIL ---
        private async void btnPerfil_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            await CargarMuroUsuario();
            this.Cursor = Cursors.Default;
        }

        // --- EVENTO PUBLICAR ---
        private void btnPublicar_Click(object sender, EventArgs e)
        {
            using (FormPublicar frm = new FormPublicar(usuarioSesion))
            {
                if (frm.ShowDialog() == DialogResult.OK)
                {
                    btnExplorar.PerformClick();
                }
            }
        }
    }
}