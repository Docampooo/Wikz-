package com.example.wikz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditarPerfil extends AppCompatActivity {

    Api api;
    Bitmap nuevaFoto = null;

    Intent intentUsuario;
    EditText txtNombre;

    EditText txtBio;

    ImageView ivfotoPerfil;

    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        api = new Api();

        intentUsuario = getIntent();

        Usuario u = (Usuario)intentUsuario.getSerializableExtra("usuario");

        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", u);

        txtNombre = findViewById(R.id.txtNombre);
        txtNombre.setText(u.getNombre());

        txtBio = findViewById(R.id.txtBio);
        txtBio.setText(u.getBiografia());

        ivfotoPerfil = findViewById(R.id.ivActualizarFotoPerfil);

        if(u.getFotoPerfil() == null){
            Bitmap fotoDefecto = BitmapFactory.decodeResource(getResources(), R.drawable.fotoperfil);
            ivfotoPerfil.setImageBitmap(fotoDefecto);
        }else{
            ivfotoPerfil.setImageBitmap(u.getFotoPerfil());
        }

        ivfotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                // Comprobamos que hay una app que pueda manejar el intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 101);
                }
            }
        });

        btnConfirmar = findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(v -> {

            u.setNombre(txtNombre.getText().toString().trim());
            u.setBiografia(txtBio.getText().toString().trim());

            if (nuevaFoto != null) {
                u.setFotoPerfil(nuevaFoto);
            }

            api.updateUsuario(this, u, success -> {
                if (success) {

                    Intent result = new Intent();
                    result.putExtra("usuario", u);
                    setResult(RESULT_OK, result);
                    finish();

                } else {
                    Toast.makeText(
                            this,
                            "Error al actualizar perfil",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(imageUri)
                );

                nuevaFoto = bitmap;
                ivfotoPerfil.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Archivo no valido",
                    Toast.LENGTH_SHORT
            );
            toast.setGravity(Gravity.CENTER, 0, 0);

            View view = toast.getView();
            view.setBackgroundResource(R.drawable.toast_background);

            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Color.WHITE);
            toast.show();
        }
    }
}