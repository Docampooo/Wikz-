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
            this.floPublicaciones = new System.Windows.Forms.FlowLayoutPanel();
            this.pnNavegador = new System.Windows.Forms.Panel();
            this.btnPerfil = new System.Windows.Forms.Button();
            this.btnPublicar = new System.Windows.Forms.Button();
            this.btnExplorar = new System.Windows.Forms.Button();
            this.pnNavegador.SuspendLayout();
            this.SuspendLayout();
            // 
            // floPublicaciones
            // 
            this.floPublicaciones.AutoScroll = true;
            this.floPublicaciones.BackColor = System.Drawing.Color.Black;
            this.floPublicaciones.Location = new System.Drawing.Point(23, 25);
            this.floPublicaciones.Name = "floPublicaciones";
            this.floPublicaciones.Size = new System.Drawing.Size(533, 636);
            this.floPublicaciones.TabIndex = 1;
            // 
            // pnNavegador
            // 
            this.pnNavegador.Controls.Add(this.btnPerfil);
            this.pnNavegador.Controls.Add(this.btnPublicar);
            this.pnNavegador.Controls.Add(this.btnExplorar);
            this.pnNavegador.Location = new System.Drawing.Point(23, 679);
            this.pnNavegador.Name = "pnNavegador";
            this.pnNavegador.Size = new System.Drawing.Size(533, 58);
            this.pnNavegador.TabIndex = 2;
            // 
            // btnPerfil
            // 
            this.btnPerfil.BackgroundImage = global::wikz_escritorio.Properties.Resources.ic_perfilMorado;
            this.btnPerfil.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.btnPerfil.Location = new System.Drawing.Point(412, 8);
            this.btnPerfil.Name = "btnPerfil";
            this.btnPerfil.Size = new System.Drawing.Size(85, 47);
            this.btnPerfil.TabIndex = 3;
            this.btnPerfil.UseVisualStyleBackColor = true;
            // 
            // btnPublicar
            // 
            this.btnPublicar.BackgroundImage = global::wikz_escritorio.Properties.Resources.ic_publicarMorado;
            this.btnPublicar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.btnPublicar.Location = new System.Drawing.Point(236, 8);
            this.btnPublicar.Name = "btnPublicar";
            this.btnPublicar.Size = new System.Drawing.Size(85, 47);
            this.btnPublicar.TabIndex = 2;
            this.btnPublicar.UseVisualStyleBackColor = true;
            // 
            // btnExplorar
            // 
            this.btnExplorar.BackgroundImage = global::wikz_escritorio.Properties.Resources.ic_explorarmorado;
            this.btnExplorar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.btnExplorar.Location = new System.Drawing.Point(32, 8);
            this.btnExplorar.Name = "btnExplorar";
            this.btnExplorar.Size = new System.Drawing.Size(85, 47);
            this.btnExplorar.TabIndex = 1;
            this.btnExplorar.UseVisualStyleBackColor = true;
            // 
            // Principal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(578, 749);
            this.Controls.Add(this.pnNavegador);
            this.Controls.Add(this.floPublicaciones);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Principal";
            this.Text = "Wikz!";
            this.pnNavegador.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.FlowLayoutPanel floPublicaciones;
        private System.Windows.Forms.Panel pnNavegador;
        private System.Windows.Forms.Button btnPerfil;
        private System.Windows.Forms.Button btnPublicar;
        private System.Windows.Forms.Button btnExplorar;
    }
}

