using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using wikz_escritorio.Modelos;
using Wikz.Services;

namespace wikz_escritorio
{
    public partial class VerPublicacion : Form
    {
        private Publicacion publicacion;
        private Usuario usuario;
        private Api api = new Api();

        List<Button> btnCirculares = new List<Button>();

        public VerPublicacion(Publicacion p, Usuario u)
        {
            InitializeComponent();

            publicacion = p;
            usuario = u;

            CargarDatos();
            ConfigurarBotonEliminar();
        }

        private async void CargarDatos()
        {
            // ---------- DATOS DE LA PUBLICACIÓN ----------
            lblNombre.Text = publicacion.Titulo;
            lblDescripcion.Text = publicacion.Descripcion;

            // ---------- IMAGEN DE LA PUBLICACIÓN ----------
            Image imgPublicacion = await api.GetFotoPublicacionAsync(publicacion.Id);

            if (imgPublicacion != null)
                pbPublicacion.Image = imgPublicacion;
            else
                pbPublicacion.Image = Properties.Resources.cora;

            // ---------- OBTENER USUARIO DESDE API ----------
            Usuario usuario = await api.GetUsuarioByIdAsync(publicacion.IdUsuario);

            if (usuario != null)
            {
                label1.Text = usuario.Nombre;

                if (!string.IsNullOrEmpty(usuario.FotoPerfilBase64))
                {
                    pbImagenUsuario.Image = api.Base64ToImage(usuario.FotoPerfilBase64);
                }
                else
                {
                    pbImagenUsuario.Image = Properties.Resources.cora;
                }
            }
        }

        private void ConfigurarBotonEliminar()
        {
            if (usuario.Id == publicacion.IdUsuario)
            {
                btnEliminar.Visible = true;
            }
            else
            {
                btnEliminar.Visible = false;
            }
        }
        private async void btnEliminar_Click(object sender, EventArgs e)
        {
            var confirmar = MessageBox.Show("¿Seguro que quieres borrar esta publicación?",
                                           "Confirmar", MessageBoxButtons.YesNo);

            if (confirmar == DialogResult.Yes)
            {
                bool borrado = await api.EliminarPublicacionAsync(publicacion.Id);
                if (borrado)
                {
                    MessageBox.Show("Publicación eliminada correctamente.");
                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
            }
        }

        // función que hace circulares los botones
        public void botonesCirculares(List<Button> botones)
        {
            foreach (Button btn in botones)
            {
                GraphicsPath path = new GraphicsPath();
                path.AddEllipse(0, 0, btn.Width, btn.Height);
                btn.Region = new Region(path);
            }
        }
    }
}
