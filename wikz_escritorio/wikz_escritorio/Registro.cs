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
    public partial class Registro : Form
    {

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
            toast.BackColor = Color.FromArgb(163, 73, 164); // moradoLogo
            toast.ForeColor = Color.White;
            toast.TextAlign = ContentAlignment.MiddleCenter;
            toast.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            toast.BorderStyle = BorderStyle.None;

            toast.Location = new Point(
                (this.Width - toast.Width) / 2,
                (this.Height - toast.Height) / 2
            );

            this.Controls.Add(toast);
            toast.BringToFront();

            Timer t = new Timer();
            t.Interval = 2000; // 2 segundos

            //Aparece 2 segundos y desaparece el Toast con la label
            t.Tick += (s, e) =>
            {
                t.Stop();
                this.Controls.Remove(toast);
                toast.Dispose();
            };
            t.Start();
        }

        string nombreUsuario = "";
        string passUsuario = "";
        private void btnLogIn_Click(object sender, EventArgs e)
        {
            //Nombre de usuario
            if (!string.IsNullOrEmpty(txtRegistroNombre.Text))
            {
                nombreUsuario = txtRegistroNombre.Text;

            }
            else
            {
                MostrarToast("El campo del nombre ha de estar cubierto");
                txtRegistroNombre.Text = "";
                nombreUsuario = "";
            }

            //Contraseña
            if (!string.IsNullOrEmpty(txtRegistroNombre.Text))
            {
                passUsuario = txtRegistroPass.Text;

            }
            else
            {
                MostrarToast("El campo de la contraseña ha de estar cubierto");
                txtRegistroPass.Text = "";
                passUsuario = "";
            }

            //Insertar consulta SQL a la base de datos para verificar si el usuario está registrado en la base de datos
            if (true)
            {
                //Enviar a la actividad principal

            }
            else
            {
                MostrarToast("Los datos del usuario no coinciden con ningún usuario registrado");

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
