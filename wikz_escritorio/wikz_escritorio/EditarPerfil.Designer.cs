namespace wikz_escritorio
{

    partial class EditarPerfil
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

        #region Windows Form Designer generated code

        private void InitializeComponent()
        {
            this.ivFotoPerfil = new System.Windows.Forms.PictureBox();
            this.txtNombre = new System.Windows.Forms.TextBox();
            this.txtBio = new System.Windows.Forms.TextBox();
            this.btnConfirmar = new System.Windows.Forms.Button();
            this.btnAtras = new System.Windows.Forms.Button();
            this.lblNombre = new System.Windows.Forms.Label();
            this.lblBio = new System.Windows.Forms.Label();
            this.lblFotoPerfil = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.ivFotoPerfil)).BeginInit();
            this.SuspendLayout();
            // 
            // ivFotoPerfil
            // 
            this.ivFotoPerfil.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(30)))), ((int)(((byte)(30)))), ((int)(((byte)(30)))));
            this.ivFotoPerfil.Cursor = System.Windows.Forms.Cursors.Hand;
            this.ivFotoPerfil.Location = new System.Drawing.Point(225, 178);
            this.ivFotoPerfil.Name = "ivFotoPerfil";
            this.ivFotoPerfil.Size = new System.Drawing.Size(150, 150);
            this.ivFotoPerfil.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.ivFotoPerfil.TabIndex = 0;
            this.ivFotoPerfil.TabStop = false;
            this.ivFotoPerfil.Click += new System.EventHandler(this.ivFotoPerfil_Click);
            this.ivFotoPerfil.Paint += new System.Windows.Forms.PaintEventHandler(this.ivFotoPerfil_Paint);
            // 
            // txtNombre
            // 
            this.txtNombre.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(40)))), ((int)(((byte)(40)))), ((int)(((byte)(40)))));
            this.txtNombre.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.txtNombre.Font = new System.Drawing.Font("Segoe UI", 12F);
            this.txtNombre.ForeColor = System.Drawing.Color.White;
            this.txtNombre.Location = new System.Drawing.Point(118, 360);
            this.txtNombre.Name = "txtNombre";
            this.txtNombre.Size = new System.Drawing.Size(364, 34);
            this.txtNombre.TabIndex = 2;
            // 
            // txtBio
            // 
            this.txtBio.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(40)))), ((int)(((byte)(40)))), ((int)(((byte)(40)))));
            this.txtBio.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.txtBio.Font = new System.Drawing.Font("Segoe UI", 12F);
            this.txtBio.ForeColor = System.Drawing.Color.White;
            this.txtBio.Location = new System.Drawing.Point(118, 450);
            this.txtBio.Multiline = true;
            this.txtBio.Name = "txtBio";
            this.txtBio.Size = new System.Drawing.Size(364, 120);
            this.txtBio.TabIndex = 3;
            // 
            // btnConfirmar
            // 
            this.btnConfirmar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.btnConfirmar.Cursor = System.Windows.Forms.Cursors.Hand;
            this.btnConfirmar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnConfirmar.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Bold);
            this.btnConfirmar.ForeColor = System.Drawing.Color.White;
            this.btnConfirmar.Location = new System.Drawing.Point(118, 620);
            this.btnConfirmar.Name = "btnConfirmar";
            this.btnConfirmar.Size = new System.Drawing.Size(364, 55);
            this.btnConfirmar.TabIndex = 4;
            this.btnConfirmar.Text = "Guardar Cambios";
            this.btnConfirmar.UseVisualStyleBackColor = false;
            this.btnConfirmar.Click += new System.EventHandler(this.btnConfirmar_Click);
            // 
            // btnAtras
            // 
            this.btnAtras.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(45)))), ((int)(((byte)(45)))), ((int)(((byte)(45)))));
            this.btnAtras.Cursor = System.Windows.Forms.Cursors.Hand;
            this.btnAtras.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnAtras.Font = new System.Drawing.Font("Segoe UI", 10F);
            this.btnAtras.ForeColor = System.Drawing.Color.White;
            this.btnAtras.Location = new System.Drawing.Point(118, 685);
            this.btnAtras.Name = "btnAtras";
            this.btnAtras.Size = new System.Drawing.Size(364, 45);
            this.btnAtras.TabIndex = 5;
            this.btnAtras.Text = "Volver Atrás";
            this.btnAtras.UseVisualStyleBackColor = false;
            this.btnAtras.Click += new System.EventHandler(this.btnAtras_Click);
            // 
            // lblNombre
            // 
            this.lblNombre.AutoSize = true;
            this.lblNombre.BackColor = System.Drawing.Color.Transparent;
            this.lblNombre.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Bold);
            this.lblNombre.ForeColor = System.Drawing.Color.White;
            this.lblNombre.Location = new System.Drawing.Point(118, 330);
            this.lblNombre.Name = "lblNombre";
            this.lblNombre.Size = new System.Drawing.Size(76, 23);
            this.lblNombre.TabIndex = 6;
            this.lblNombre.Text = "Nombre";
            // 
            // lblBio
            // 
            this.lblBio.AutoSize = true;
            this.lblBio.BackColor = System.Drawing.Color.Transparent;
            this.lblBio.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Bold);
            this.lblBio.ForeColor = System.Drawing.Color.White;
            this.lblBio.Location = new System.Drawing.Point(118, 420);
            this.lblBio.Name = "lblBio";
            this.lblBio.Size = new System.Drawing.Size(84, 23);
            this.lblBio.TabIndex = 7;
            this.lblBio.Text = "Biografía";
            // 
            // lblFotoPerfil
            // 
            this.lblFotoPerfil.AutoSize = true;
            this.lblFotoPerfil.BackColor = System.Drawing.Color.Transparent;
            this.lblFotoPerfil.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Bold);
            this.lblFotoPerfil.ForeColor = System.Drawing.Color.White;
            this.lblFotoPerfil.Location = new System.Drawing.Point(255, 143);
            this.lblFotoPerfil.Name = "lblFotoPerfil";
            this.lblFotoPerfil.Size = new System.Drawing.Size(93, 23);
            this.lblFotoPerfil.TabIndex = 8;
            this.lblFotoPerfil.Text = "Foto Perfil";
            // 
            // EditarPerfil
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.BackgroundImage = global::wikz_escritorio.Properties.Resources.registro;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(600, 800);
            this.Controls.Add(this.lblFotoPerfil);
            this.Controls.Add(this.lblBio);
            this.Controls.Add(this.lblNombre);
            this.Controls.Add(this.btnAtras);
            this.Controls.Add(this.btnConfirmar);
            this.Controls.Add(this.txtBio);
            this.Controls.Add(this.txtNombre);
            this.Controls.Add(this.ivFotoPerfil);
            this.DoubleBuffered = true;
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.Name = "EditarPerfil";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Wikz - Editar Perfil";
            ((System.ComponentModel.ISupportInitialize)(this.ivFotoPerfil)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox ivFotoPerfil;
        private System.Windows.Forms.TextBox txtNombre;
        private System.Windows.Forms.TextBox txtBio;
        private System.Windows.Forms.Button btnConfirmar;
        private System.Windows.Forms.Button btnAtras;
        private System.Windows.Forms.Label lblNombre;
        private System.Windows.Forms.Label lblBio;
        private System.Windows.Forms.Label lblFotoPerfil;
    }
}