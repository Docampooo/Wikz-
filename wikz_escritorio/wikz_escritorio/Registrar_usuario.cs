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

        bool usuarioValido = true;

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
                txtAñadirNombre.Text = "";
                nombreUsuario = "";
                usuarioValido = false;
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
                txtAñadirPass.Text = "";
                txtAñadirRepetirPass.Text = "";

                passUsuario = "";
                repPassUsuario = "";
                usuarioValido = false;
            }

            if (passUsuario == repPassUsuario)
            {
                igual = true;
            }
            else
            {

                reg.MostrarToast("Las contraseñas no coinciden");
                txtAñadirPass.Text = "";
                txtAñadirRepetirPass.Text = "";

                passUsuario = "";
                repPassUsuario = "";
                usuarioValido = false;
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
                    txtAñadirCorreo.Text = "";
                    correo = "";
                    usuarioValido = false;

                }
            }
            else
            {
                reg.MostrarToast("El campo del correo Electronico ha de estar cubierto");
                usuarioValido = false;
            }

            Usuario u = new Usuario(nombreUsuario, passUsuario, correo, "", null);
            //Añadir consulta SQL para comprobar si hay algún usuario con los mismos datos, si da true, boolean usuariovalido a true
            usuarioValido = true;

            
        }
    }
}
