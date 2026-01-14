package com.example.wikz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class AnadirUsuario extends AppCompatActivity {

    EditText txtRegistrarNombre;
    EditText txtRegistrarPass;
    EditText txtRepetirPass;
    EditText txtRegistrarCorreo;

    Button btnRegistrarUsuario;
    String nombreUsuario;
    String pass;

    String confirmarPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.anadir_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtRegistrarNombre = findViewById(R.id.txtRegistrarNombre);

        txtRegistrarPass = findViewById(R.id.txtRegistrarPass);

        txtRepetirPass = findViewById(R.id.txtRepetirPass);

        txtRegistrarCorreo = findViewById(R.id.txtRegistrarCorreo);

        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);

        nombreUsuario = "";
        pass = "";
        confirmarPass = "";

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreUsuario = txtRegistrarNombre.getText().toString();
                pass = txtRegistrarPass.getText().toString().trim();
                confirmarPass = txtRepetirPass.getText().toString().trim();

                if(pass.equals(confirmarPass)){

                    //Añadir el usuario a la base de datos y entrar en la pagina principal
                    Intent intentPrincipal = new Intent(AnadirUsuario.this, AnadirUsuario.class);

                    startActivity(intentPrincipal);
                }else{

                    //Lanzar mensaje de Error
                    Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden!", Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.toast_background);

                    // Cambiar color del texto a blanco para que contraste
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextColor(Color.WHITE);

                    toast.show();

                    nombreUsuario = "";
                    pass = "";
                    confirmarPass = "";

                    txtRegistrarNombre.setText("");
                    txtRegistrarPass.setText("");
                    txtRepetirPass.setText("");
                    txtRegistrarCorreo.setText("");
                }
            }
        });
    }
}