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

public class Registro extends AppCompatActivity {

    Api api;

    Usuario u = new Usuario();
    EditText txtNombreUsuario;
    EditText txtPassUsuario;

    Button btnLogIn;

    Button btnSignUp;
    String nombreUsuario;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //TextViews
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtPassUsuario = findViewById(R.id.txtPassUsuario);

        //Botones
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        //datos Usuario
        nombreUsuario = "";
        pass = "";
        api = new Api();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreUsuario = txtNombreUsuario.getText().toString().trim();
                pass = txtPassUsuario.getText().toString().trim();

                if(nombreUsuario.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Registro.this, "Por favor, escribe tu nombre y contraseña", Toast.LENGTH_SHORT).show();
                    
                }else{

                    System.out.println(nombreUsuario + " " + pass);
                    //Probar!
                    api.getUsuarioNombrePass(Registro.this, nombreUsuario, pass, (success, usu) -> {

                        if(success){
                            System.out.println("Usuario encontrado");
                            u = usu;

                            // LOGIN OK
                            Intent intentPrincipal = new Intent(Registro.this, MenuPrincipal.class);
                            intentPrincipal.putExtra("usuario", u);
                            startActivity(intentPrincipal);

                        }else{

                            // LOGIN ERROR
                            txtNombreUsuario.setText("");
                            txtPassUsuario.setText("");
                        }
                    });

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "El usuario y la contraseña no coinciden",
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
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Iniciar Actividad de registrar usuario
                Intent intentRegistro = new Intent(Registro.this, AnadirUsuario.class);

                startActivity(intentRegistro);
            }
        });
    }
}