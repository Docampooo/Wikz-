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
    public partial class RecyclerView : UserControl
    {
        public RecyclerView(Publicacion p)
        {
            InitializeComponent();

            pbImagen.Image = p.FotoPublicacion;
        }
    }
}
