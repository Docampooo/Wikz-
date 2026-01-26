namespace wikz_escritorio
{
    partial class VerPublicacion
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(VerPublicacion));
            this.pbPublicacion = new System.Windows.Forms.PictureBox();
            this.btnLike = new System.Windows.Forms.Button();
            this.btnGuardar = new System.Windows.Forms.Button();
            this.lblDescripcion = new System.Windows.Forms.Label();
            this.lblCreacion = new System.Windows.Forms.Label();
            this.lblNombre = new System.Windows.Forms.Label();
            this.lblLikes = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.pbPublicacion)).BeginInit();
            this.SuspendLayout();
            // 
            // pbPublicacion
            // 
            this.pbPublicacion.BackColor = System.Drawing.Color.White;
            this.pbPublicacion.Image = global::wikz_escritorio.Properties.Resources.outfit;
            this.pbPublicacion.Location = new System.Drawing.Point(63, 34);
            this.pbPublicacion.Name = "pbPublicacion";
            this.pbPublicacion.Size = new System.Drawing.Size(450, 450);
            this.pbPublicacion.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pbPublicacion.TabIndex = 0;
            this.pbPublicacion.TabStop = false;
            // 
            // btnLike
            // 
            this.btnLike.BackgroundImage = global::wikz_escritorio.Properties.Resources.ic_likesMorado;
            this.btnLike.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.btnLike.Location = new System.Drawing.Point(105, 496);
            this.btnLike.Name = "btnLike";
            this.btnLike.Size = new System.Drawing.Size(70, 70);
            this.btnLike.TabIndex = 1;
            this.btnLike.UseVisualStyleBackColor = true;
            // 
            // btnGuardar
            // 
            this.btnGuardar.BackgroundImage = global::wikz_escritorio.Properties.Resources.ic_guardarMorado;
            this.btnGuardar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.btnGuardar.Location = new System.Drawing.Point(405, 496);
            this.btnGuardar.Name = "btnGuardar";
            this.btnGuardar.Size = new System.Drawing.Size(70, 70);
            this.btnGuardar.TabIndex = 2;
            this.btnGuardar.UseVisualStyleBackColor = true;
            // 
            // lblDescripcion
            // 
            this.lblDescripcion.AutoSize = true;
            this.lblDescripcion.BackColor = System.Drawing.Color.Black;
            this.lblDescripcion.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblDescripcion.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.lblDescripcion.Location = new System.Drawing.Point(59, 644);
            this.lblDescripcion.Name = "lblDescripcion";
            this.lblDescripcion.Size = new System.Drawing.Size(104, 22);
            this.lblDescripcion.TabIndex = 3;
            this.lblDescripcion.Text = "Descripcion";
            this.lblDescripcion.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblCreacion
            // 
            this.lblCreacion.AutoSize = true;
            this.lblCreacion.BackColor = System.Drawing.Color.Black;
            this.lblCreacion.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblCreacion.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.lblCreacion.Location = new System.Drawing.Point(431, 589);
            this.lblCreacion.Name = "lblCreacion";
            this.lblCreacion.Size = new System.Drawing.Size(82, 22);
            this.lblCreacion.TabIndex = 4;
            this.lblCreacion.Text = "Creacion";
            this.lblCreacion.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblNombre
            // 
            this.lblNombre.AutoSize = true;
            this.lblNombre.BackColor = System.Drawing.Color.Black;
            this.lblNombre.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblNombre.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.lblNombre.Location = new System.Drawing.Point(59, 589);
            this.lblNombre.Name = "lblNombre";
            this.lblNombre.Size = new System.Drawing.Size(73, 22);
            this.lblNombre.TabIndex = 5;
            this.lblNombre.Text = "Nombre";
            this.lblNombre.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblLikes
            // 
            this.lblLikes.AutoSize = true;
            this.lblLikes.BackColor = System.Drawing.Color.Black;
            this.lblLikes.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLikes.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(163)))), ((int)(((byte)(73)))), ((int)(((byte)(164)))));
            this.lblLikes.Location = new System.Drawing.Point(181, 518);
            this.lblLikes.Name = "lblLikes";
            this.lblLikes.Size = new System.Drawing.Size(20, 22);
            this.lblLikes.TabIndex = 7;
            this.lblLikes.Text = "0";
            this.lblLikes.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // VerPublicacion
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(582, 753);
            this.Controls.Add(this.lblLikes);
            this.Controls.Add(this.lblNombre);
            this.Controls.Add(this.lblCreacion);
            this.Controls.Add(this.lblDescripcion);
            this.Controls.Add(this.btnGuardar);
            this.Controls.Add(this.btnLike);
            this.Controls.Add(this.pbPublicacion);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "VerPublicacion";
            this.Text = "VerPublicacion";
            ((System.ComponentModel.ISupportInitialize)(this.pbPublicacion)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox pbPublicacion;
        private System.Windows.Forms.Button btnLike;
        private System.Windows.Forms.Button btnGuardar;
        private System.Windows.Forms.Label lblDescripcion;
        private System.Windows.Forms.Label lblCreacion;
        private System.Windows.Forms.Label lblNombre;
        private System.Windows.Forms.Label lblLikes;
    }
}