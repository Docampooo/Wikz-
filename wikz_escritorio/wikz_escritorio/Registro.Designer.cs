namespace wikz_escritorio
{
    partial class Registro
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Registro));
            this.pbRegistro = new System.Windows.Forms.PictureBox();
            this.txtRegistroNombre = new System.Windows.Forms.TextBox();
            this.btnLogIn = new System.Windows.Forms.Button();
            this.btnSignUp = new System.Windows.Forms.Button();
            this.txtRegistroPass = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.pbRegistro)).BeginInit();
            this.SuspendLayout();
            // 
            // pbRegistro
            // 
            this.pbRegistro.BackColor = System.Drawing.Color.Black;
            this.pbRegistro.Image = global::wikz_escritorio.Properties.Resources.logosinfondo1;
            this.pbRegistro.Location = new System.Drawing.Point(166, 126);
            this.pbRegistro.Name = "pbRegistro";
            this.pbRegistro.Size = new System.Drawing.Size(148, 135);
            this.pbRegistro.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pbRegistro.TabIndex = 0;
            this.pbRegistro.TabStop = false;
            // 
            // txtRegistroNombre
            // 
            this.txtRegistroNombre.BackColor = System.Drawing.Color.Black;
            this.txtRegistroNombre.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtRegistroNombre.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.txtRegistroNombre.Location = new System.Drawing.Point(85, 295);
            this.txtRegistroNombre.Name = "txtRegistroNombre";
            this.txtRegistroNombre.Size = new System.Drawing.Size(308, 22);
            this.txtRegistroNombre.TabIndex = 1;
            this.txtRegistroNombre.Text = "Nombre de Usuario";
            this.txtRegistroNombre.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // btnLogIn
            // 
            this.btnLogIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.btnLogIn.FlatAppearance.BorderSize = 0;
            this.btnLogIn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnLogIn.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnLogIn.Location = new System.Drawing.Point(85, 486);
            this.btnLogIn.Name = "btnLogIn";
            this.btnLogIn.Size = new System.Drawing.Size(308, 30);
            this.btnLogIn.TabIndex = 2;
            this.btnLogIn.Text = "Log In";
            this.btnLogIn.UseVisualStyleBackColor = false;
            // 
            // btnSignUp
            // 
            this.btnSignUp.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.btnSignUp.FlatAppearance.BorderSize = 0;
            this.btnSignUp.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnSignUp.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSignUp.Location = new System.Drawing.Point(85, 536);
            this.btnSignUp.Name = "btnSignUp";
            this.btnSignUp.Size = new System.Drawing.Size(308, 30);
            this.btnSignUp.TabIndex = 3;
            this.btnSignUp.Text = "Sign Up";
            this.btnSignUp.UseVisualStyleBackColor = false;
            // 
            // txtRegistroPass
            // 
            this.txtRegistroPass.BackColor = System.Drawing.Color.Black;
            this.txtRegistroPass.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtRegistroPass.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.txtRegistroPass.Location = new System.Drawing.Point(85, 352);
            this.txtRegistroPass.Name = "txtRegistroPass";
            this.txtRegistroPass.Size = new System.Drawing.Size(308, 22);
            this.txtRegistroPass.TabIndex = 4;
            this.txtRegistroPass.Text = "Contraseña";
            this.txtRegistroPass.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // Registro
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = global::wikz_escritorio.Properties.Resources.registro;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(471, 690);
            this.Controls.Add(this.txtRegistroPass);
            this.Controls.Add(this.btnSignUp);
            this.Controls.Add(this.btnLogIn);
            this.Controls.Add(this.txtRegistroNombre);
            this.Controls.Add(this.pbRegistro);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Registro";
            this.Text = "Registro";
            ((System.ComponentModel.ISupportInitialize)(this.pbRegistro)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox pbRegistro;
        private System.Windows.Forms.TextBox txtRegistroNombre;
        private System.Windows.Forms.Button btnLogIn;
        private System.Windows.Forms.Button btnSignUp;
        private System.Windows.Forms.TextBox txtRegistroPass;
    }
}