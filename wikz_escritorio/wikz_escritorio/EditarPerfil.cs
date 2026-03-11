using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Threading.Tasks;
using System.Windows.Forms;
using Wikz.Services;
using wikz_escritorio.Modelos;

namespace wikz_escritorio
{
    public partial class EditarPerfil : Form
    {
        private Usuario u;
        private Api api = new Api();
        public Usuario UsuarioEditado { get; private set; }
        private Image nuevaFoto = null;

        public EditarPerfil(Usuario usuarioActual)
        {
            InitializeComponent();
            this.u = usuarioActual;

            CargarDatos();
        }

        private void CargarDatos()
        {
            txtNombre.Text = u.Nombre;
            txtBio.Text = u.Biografia;

            if (!string.IsNullOrEmpty(u.FotoPerfilBase64))
            {
                try
                {
                    byte[] imageBytes = Convert.FromBase64String(u.FotoPerfilBase64);
                    using (var ms = new System.IO.MemoryStream(imageBytes))
                    {
                        ivFotoPerfil.Image = Image.FromStream(ms);
                    }
                }
                catch
                {
                    ivFotoPerfil.Image = Properties.Resources.cora;
                }
            }
            else
            {
                ivFotoPerfil.Image = Properties.Resources.cora;
            }
        }

        private void ivFotoPerfil_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog ofd = new OpenFileDialog())
            {
                ofd.Title = "Seleccionar Foto de Perfil";
                ofd.Filter = "Imágenes|*.jpg;*.jpeg;*.png";
                if (ofd.ShowDialog() == DialogResult.OK)
                {
                    // Cargamos la imagen de forma que no bloquee el archivo en el disco
                    using (var temp = Image.FromFile(ofd.FileName))
                    {
                        nuevaFoto = new Bitmap(temp);
                    }
                    ivFotoPerfil.Image = nuevaFoto;
                }
            }
        }

        private async void btnConfirmar_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(txtNombre.Text))
            {
                MessageBox.Show("El nombre no puede estar vacío.");
                return;
            }

            this.Cursor = Cursors.WaitCursor;
            btnConfirmar.Enabled = false;

            try
            {
                u.Nombre = txtNombre.Text.Trim();
                u.Biografia = txtBio.Text.Trim();

                if (nuevaFoto != null)
                {
                    u.FotoPerfilBase64 = api.ImageToBase64(nuevaFoto);
                }

                // intentar la actualización
                bool exito = await api.UpdateUsuarioAsync(u, nuevaFoto);

                if (exito)
                {
                    this.UsuarioEditado = u;
                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
                else
                {
                    MessageBox.Show("No se pudo actualizar el perfil. Revisa la conexión.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
            finally
            {
                this.Cursor = Cursors.Default;
                btnConfirmar.Enabled = true;
            }
        }

        private void btnAtras_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void ivFotoPerfil_Paint(object sender, PaintEventArgs e)
        {
            // Antialiasing para que el círculo no se vea pixelado
            e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;
            GraphicsPath gp = new GraphicsPath();
            gp.AddEllipse(0, 0, ivFotoPerfil.Width - 1, ivFotoPerfil.Height - 1);
            ivFotoPerfil.Region = new Region(gp);
        }
    }
}