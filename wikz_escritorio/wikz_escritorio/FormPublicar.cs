using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Wikz.Services;
using wikz_escritorio.Modelos;

namespace wikz_escritorio
{
    public partial class FormPublicar : Form
    {
        private Usuario usuarioSesion;
        private Api api = new Api();
        private Image imagenSeleccionada = null;

        public FormPublicar(Usuario u)
        {
            InitializeComponent();
            usuarioSesion = u;
            ivImagen.Image = Properties.Resources.cora;


            this.StartPosition = FormStartPosition.CenterScreen;
        }

        private void ivImagen_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog ofd = new OpenFileDialog())
            {
                ofd.Title = "Seleccionar Imagen";
                ofd.Filter = "Imágenes|*.jpg;*.jpeg;*.png";

                if (ofd.ShowDialog() == DialogResult.OK)
                {
                    using (var temp = Image.FromFile(ofd.FileName))
                    {
                        imagenSeleccionada = new Bitmap(temp);
                    }

                    ivImagen.Image = imagenSeleccionada;
                }
            }
        }

        private async void btnPublicar_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(txtTitulo.Text))
            {
                MessageBox.Show("Debes escribir un título.");
                return;
            }

            if (imagenSeleccionada == null)
            {
                MessageBox.Show("Debes seleccionar una imagen.");
                return;
            }

            this.Cursor = Cursors.WaitCursor;
            btnPublicar.Enabled = false;

            try
            {
                bool exito = await api.AddPublicacionAsync(
                    usuarioSesion.Id,
                    txtTitulo.Text.Trim(),
                    imagenSeleccionada,
                    txtDescripcion.Text.Trim()
                );

                if (exito)
                {
                    MessageBox.Show("Publicación creada correctamente.");
                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
                else
                {
                    MessageBox.Show("No se pudo publicar.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
            finally
            {
                this.Cursor = Cursors.Default;
                btnPublicar.Enabled = true;
            }
        }

        private void ivImagen_Paint(object sender, PaintEventArgs e)
        {
            e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;

            GraphicsPath gp = new GraphicsPath();
            gp.AddEllipse(0, 0, ivImagen.Width - 1, ivImagen.Height - 1);
            ivImagen.Region = new Region(gp);
        }
    }
}
