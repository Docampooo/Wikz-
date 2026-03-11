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
using wikz_escritorio.Modelos;
using System.Drawing.Drawing2D;

namespace wikz_escritorio
{
    public partial class CabeceraPerfil : UserControl
    {
        Usuario u;
        public CabeceraPerfil(Usuario u)
        {
            InitializeComponent();

            this.u = u;
            tvNombrePerfil.Text = u.Nombre;
            tvDescripcionPerfil.Text = u.Biografia;

            // Cargar imagen si existe
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

            this.Resize += (s, e) => CentrarControles();
            CentrarControles();
        }
        private void CentrarControles()
        {
            // centrar la Foto
            ivFotoPerfil.Left = (this.Width - ivFotoPerfil.Width) / 2;

            // centrar el Nombre
            tvNombrePerfil.Width = this.Width;
            tvNombrePerfil.Left = 0;

            // Centrar la Descripción
            tvDescripcionPerfil.Width = (int)(this.Width * 0.8);
            tvDescripcionPerfil.Left = (this.Width - tvDescripcionPerfil.Width) / 2;

            // centrar el Botón
            btnEditarPerfil.Left = (this.Width - btnEditarPerfil.Width) / 2;
        }

        private void ivFotoPerfil_Paint(object sender, PaintEventArgs e)
        {
            GraphicsPath gp = new GraphicsPath();
            gp.AddEllipse(0, 0, ivFotoPerfil.Width - 1, ivFotoPerfil.Height - 1);
            ivFotoPerfil.Region = new Region(gp);
        }
        private void btnEditarPerfil_Click(object sender, EventArgs e)
        {
            if (this.ParentForm is Principal formularioPrincipal)
            {
                using (EditarPerfil frmEdit = new EditarPerfil(u))
                {
                    if (frmEdit.ShowDialog() == DialogResult.OK)
                    {
                        //Si el usuario guardó cambios se actualiza el usuario en Principal
                        formularioPrincipal.ActualizarUsuarioSesion(frmEdit.UsuarioEditado);
                    }
                }
            }
        }
    }
}
