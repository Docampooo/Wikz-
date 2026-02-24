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

            this.usuarioSesion = u;

            CargarMuroExplorar();
        }

        #region --- Lógica de Navegación (Equivalente a Fragments) ---

        private async void btnExplorar_Click(object sender, EventArgs e)
        {
            await CargarMuroExplorar();
        }

        private void btnPublicar_Click(object sender, EventArgs e)
        {
            // Abrir el formulario de subir publicación (como un Dialog)
            using (var formPublicar = new FormPublicar(usuarioSesion))
            {
                if (formPublicar.ShowDialog() == DialogResult.OK)
                {
                    CargarMuroExplorar(); // Refrescar al volver
                }
            }
        }

        private async void btnPerfil_Click(object sender, EventArgs e)
        {
            // Cargamos solo las publicaciones del usuario logueado
            await CargarMuroUsuario();
        }

        #endregion

        #region --- Carga de Datos desde API ---

        private async Task CargarMuroExplorar()
        {
            floPublicaciones.Controls.Clear();
            floPublicaciones.SuspendLayout();

            // Obtenemos todas las publicaciones de la API
            List<Publicacion> lista = await api.GetPublicacionesAsync();

            foreach (Publicacion p in lista)
            {
                // Instanciamos el UserControl que creamos anteriormente
                RecyclerView item = new RecyclerView(p);

                // Ajustamos el tamaño para que quepan 2 o 3 por fila
                item.Width = (int)(floPublicaciones.Width * 0.45);
                item.Height = 220;

                floPublicaciones.Controls.Add(item);
            }

            floPublicaciones.ResumeLayout();
        }

        private async Task CargarMuroUsuario()
        {
            floPublicaciones.Controls.Clear();
            floPublicaciones.SuspendLayout();

            // filtrar por ID
            List<Publicacion> misPublis = await api.GetPublicacionesUsuarioAsync(usuarioSesion.Id);

            foreach (Publicacion p in misPublis)
            {
                RecyclerView item = new RecyclerView(p);
                item.Width = (int)(floPublicaciones.Width * 0.45);
                item.Height = 220;
                floPublicaciones.Controls.Add(item);
            }

            floPublicaciones.ResumeLayout();
        }

        #endregion

        #region --- Diseño (Tus métodos de dibujo) ---
        // ... (Mantén aquí tus funciones RedondearPnNavegador y PnNavegador_Paint igual que las tenías)
        #endregion
    }
}