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

namespace wikz_escritorio
{
    public partial class VerPublicacion : Form
    {
        List<Button> btnCirculares = new List<Button>();
        public VerPublicacion(Publicacion p)
        {
            InitializeComponent();

            lblCreacion.Text = p.FechaCreacion.ToString();
            lblDescripcion.Text = p.Descripcion;
        }

        //funcion que hace circulares los botones
        public void botonesCirculares(List<Button> botones)
        {
            foreach(Button btn in botones)
            {
                GraphicsPath path = new GraphicsPath();
                path.AddEllipse(0, 0, btn.Width, btn.Height);
                btn.Region = new Region(path);
            }
        }
    }
}
