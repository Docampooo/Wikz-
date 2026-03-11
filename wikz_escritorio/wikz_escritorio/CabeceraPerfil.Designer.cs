namespace wikz_escritorio
{
    partial class CabeceraPerfil
    {
        private System.ComponentModel.IContainer components = null;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de componentes

        private void InitializeComponent()
        {
            this.ivFotoPerfil = new System.Windows.Forms.PictureBox();
            this.tvNombrePerfil = new System.Windows.Forms.Label();
            this.tvDescripcionPerfil = new System.Windows.Forms.Label();
            this.btnEditarPerfil = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.ivFotoPerfil)).BeginInit();
            this.SuspendLayout();
            // 
            // ivFotoPerfil
            // 
            this.ivFotoPerfil.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(30)))), ((int)(((byte)(30)))), ((int)(((byte)(30)))));
            this.ivFotoPerfil.Location = new System.Drawing.Point(225, 15);
            this.ivFotoPerfil.Name = "ivFotoPerfil";
            this.ivFotoPerfil.Size = new System.Drawing.Size(100, 100);
            this.ivFotoPerfil.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.ivFotoPerfil.TabIndex = 0;
            this.ivFotoPerfil.TabStop = false;
            this.ivFotoPerfil.Paint += new System.Windows.Forms.PaintEventHandler(this.ivFotoPerfil_Paint);
            // 
            // tvNombrePerfil
            // 
            this.tvNombrePerfil.Font = new System.Drawing.Font("Segoe UI", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tvNombrePerfil.ForeColor = System.Drawing.Color.White;
            this.tvNombrePerfil.Location = new System.Drawing.Point(0, 120);
            this.tvNombrePerfil.Name = "tvNombrePerfil";
            this.tvNombrePerfil.Size = new System.Drawing.Size(550, 30);
            this.tvNombrePerfil.TabIndex = 1;
            this.tvNombrePerfil.Text = "Nombre de Usuario";
            this.tvNombrePerfil.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tvDescripcionPerfil
            // 
            this.tvDescripcionPerfil.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tvDescripcionPerfil.ForeColor = System.Drawing.Color.Silver;
            this.tvDescripcionPerfil.Location = new System.Drawing.Point(50, 155);
            this.tvDescripcionPerfil.Name = "tvDescripcionPerfil";
            this.tvDescripcionPerfil.Size = new System.Drawing.Size(450, 45);
            this.tvDescripcionPerfil.TabIndex = 2;
            this.tvDescripcionPerfil.Text = "Biografia del perfil";
            this.tvDescripcionPerfil.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // btnEditarPerfil
            // 
            this.btnEditarPerfil.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.btnEditarPerfil.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnEditarPerfil.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold);
            this.btnEditarPerfil.ForeColor = System.Drawing.Color.White;
            this.btnEditarPerfil.Location = new System.Drawing.Point(200, 210);
            this.btnEditarPerfil.Name = "btnEditarPerfil";
            this.btnEditarPerfil.Size = new System.Drawing.Size(150, 35);
            this.btnEditarPerfil.TabIndex = 3;
            this.btnEditarPerfil.Text = "Editar Perfil";
            this.btnEditarPerfil.UseVisualStyleBackColor = false;
            this.btnEditarPerfil.Click += new System.EventHandler(this.btnEditarPerfil_Click);
            // 
            // CabeceraPerfil
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.Controls.Add(this.btnEditarPerfil);
            this.Controls.Add(this.tvDescripcionPerfil);
            this.Controls.Add(this.tvNombrePerfil);
            this.Controls.Add(this.ivFotoPerfil);
            this.Name = "CabeceraPerfil";
            this.Size = new System.Drawing.Size(550, 260);
            ((System.ComponentModel.ISupportInitialize)(this.ivFotoPerfil)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox ivFotoPerfil;
        private System.Windows.Forms.Label tvNombrePerfil;
        private System.Windows.Forms.Label tvDescripcionPerfil;
        private System.Windows.Forms.Button btnEditarPerfil;
    }
}