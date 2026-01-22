namespace wikz_escritorio
{
    partial class Principal
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Principal));
            this.floColecciones = new System.Windows.Forms.FlowLayoutPanel();
            this.floPublicaciones = new System.Windows.Forms.FlowLayoutPanel();
            this.floNavegador = new System.Windows.Forms.FlowLayoutPanel();
            this.SuspendLayout();
            // 
            // floColecciones
            // 
            this.floColecciones.AutoScroll = true;
            this.floColecciones.BackColor = System.Drawing.Color.Gray;
            this.floColecciones.Location = new System.Drawing.Point(23, 24);
            this.floColecciones.Name = "floColecciones";
            this.floColecciones.Size = new System.Drawing.Size(533, 98);
            this.floColecciones.TabIndex = 0;
            // 
            // floPublicaciones
            // 
            this.floPublicaciones.AutoScroll = true;
            this.floPublicaciones.BackColor = System.Drawing.Color.Gray;
            this.floPublicaciones.Location = new System.Drawing.Point(23, 145);
            this.floPublicaciones.Name = "floPublicaciones";
            this.floPublicaciones.Size = new System.Drawing.Size(533, 434);
            this.floPublicaciones.TabIndex = 1;
            // 
            // floNavegador
            // 
            this.floNavegador.AutoScroll = true;
            this.floNavegador.BackColor = System.Drawing.Color.Gray;
            this.floNavegador.Location = new System.Drawing.Point(23, 605);
            this.floNavegador.Name = "floNavegador";
            this.floNavegador.Size = new System.Drawing.Size(533, 74);
            this.floNavegador.TabIndex = 1;
            // 
            // Principal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.ClientSize = new System.Drawing.Size(582, 703);
            this.Controls.Add(this.floNavegador);
            this.Controls.Add(this.floPublicaciones);
            this.Controls.Add(this.floColecciones);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Principal";
            this.Text = "Wikz!";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.FlowLayoutPanel floColecciones;
        private System.Windows.Forms.FlowLayoutPanel floPublicaciones;
        private System.Windows.Forms.FlowLayoutPanel floNavegador;
    }
}

