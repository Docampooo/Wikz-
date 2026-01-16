using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.ListView;

namespace wikz_escritorio
{
    public partial class Registrar_usuario : Form
    {
        Registro reg;
        public Registrar_usuario(Registro reg)
        {
            InitializeComponent();

            this.reg = reg;
        }

        //funcion optima para comprobar la validez del correo electronico
        private bool comprobarCorreo(string correo)
        {

            if (string.IsNullOrWhiteSpace(correo))
            {
                return false;

            }

            string patron = @"^[^@\s]+@[^@\s]+\.[^@\s]+$";

            return Regex.IsMatch(correo, patron);
        }

        string nombreUsuario = "";

        string passUsuario = "";
        string repPassUsuario = "";
        bool igual = false;

        string correo = "";

        bool usuarioValido = false;

        private void btnSignUp_Click(object sender, EventArgs e)
        {
            //Nombre de usuario
            if (!string.IsNullOrEmpty(txtAñadirNombre.Text))
            {
                nombreUsuario = txtAñadirNombre.Text;

            }
            else
            {
                reg.MostrarToast("El campo del nombre ha de estar cubierto");
            }

            //Contraseña
            if (!string.IsNullOrEmpty(txtAñadirPass.Text) && !string.IsNullOrEmpty(txtAñadirRepetirPass.Text))
            {
                passUsuario = txtAñadirPass.Text;
                repPassUsuario = txtAñadirRepetirPass.Text;
            }
            else
            {
                reg.MostrarToast("El campo de la contraseña ha de estar cubierto");
            }

            if (passUsuario == repPassUsuario)
            {
                igual = true;
            }
            else
            {
                passUsuario = "";
                repPassUsuario = "";

                txtAñadirPass.Text = "";
                txtAñadirRepetirPass.Text = "";

                reg.MostrarToast("Las contraseñas no coinciden");
            }

            //Correo electrónico
            if (string.IsNullOrEmpty(txtAñadirCorreo.Text))
            {
                if (comprobarCorreo(txtAñadirCorreo.Text))
                {
                    correo = txtAñadirNombre.Text;

                }
                else
                {
                    reg.MostrarToast("El correo es incorrecto");

                    correo = "";
                    txtAñadirCorreo.Text = "";
                }
            }
            else
            {
                reg.MostrarToast("El campo del correo Electronico ha de estar cubierto");
            }

            //Añadir consulta SQL para comprobar si hay algún usuario con los mismos datos
        }
    }
}
