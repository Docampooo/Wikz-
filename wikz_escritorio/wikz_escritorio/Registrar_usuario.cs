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
using Wikz.Services;
using wikz_escritorio.Modelos;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.ListView;

namespace wikz_escritorio
{
    public partial class Registrar_usuario : Form
    {
        private Registro reg;
        private Api api = new Api(); // Instancia de la API

        public Registrar_usuario(Registro reg)
        {
            InitializeComponent();
            this.reg = reg;

            // Evento para cuando cierren la ventana con la "X"
            this.FormClosed += (s, e) => reg.Show();
        }

        private bool comprobarCorreo(string correo)
        {
            if (string.IsNullOrWhiteSpace(correo)) return false;
            string patron = @"^[^@\s]+@[^@\s]+\.[^@\s]+$";
            return Regex.IsMatch(correo, patron);
        }

        private async void btnSignUp_Click(object sender, EventArgs e)
        {
            // 1. Recoger datos y limpiar espacios
            string nombreUsuario = txtAñadirNombre.Text.Trim();
            string passUsuario = txtAñadirPass.Text;
            string repPassUsuario = txtAñadirRepetirPass.Text;
            string correo = txtAñadirCorreo.Text.Trim();
            bool usuarioValido = true;

            // 2. Validaciones de UI
            if (string.IsNullOrEmpty(nombreUsuario))
            {
                reg.MostrarToast("El nombre es obligatorio");
                usuarioValido = false;
            }
            else if (string.IsNullOrEmpty(passUsuario) || string.IsNullOrEmpty(repPassUsuario))
            {
                reg.MostrarToast("Cubra ambos campos de contraseña");
                usuarioValido = false;
            }
            else if (passUsuario != repPassUsuario)
            {
                reg.MostrarToast("Las contraseñas no coinciden");
                usuarioValido = false;
            }
            else if (string.IsNullOrEmpty(correo) || !comprobarCorreo(correo))
            {
                reg.MostrarToast("El correo es incorrecto u obligatorio");
                usuarioValido = false;
            }

            if (usuarioValido)
            {
                try
                {
                    this.Cursor = Cursors.WaitCursor;
                    btnSignUp.Enabled = false;

                    bool exito = await api.AddUsuarioAsync(nombreUsuario, correo, passUsuario, "");

                    if (exito)
                    {
                        Usuario usuarioRecienCreado = await api.GetUsuarioNombrePassAsync(nombreUsuario, passUsuario);

                        if (usuarioRecienCreado != null)
                        {
                            reg.MostrarToast("¡Bienvenido a Wikz!");

                            Principal pantallaPrincipal = new Principal(usuarioRecienCreado);
                            pantallaPrincipal.Show();

                            this.Hide();
                            reg.Hide();
                        }
                        else
                        {
                            // Si por algo falla el login automático, volvemos al login normal
                            reg.MostrarToast("Usuario creado. Por favor, inicia sesión.");
                            this.Close();
                        }
                    }
                    else
                    {
                        reg.MostrarToast("Error: El nombre o correo ya están en uso");
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error al conectar con Wikz: " + ex.Message);
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                    btnSignUp.Enabled = true;
                }
            }
        }

        private void Registrar_usuario_Load(object sender, EventArgs e)
        {
            // Aquí puedes inicializar colores o focos de texto si quieres
        }
    }
}
