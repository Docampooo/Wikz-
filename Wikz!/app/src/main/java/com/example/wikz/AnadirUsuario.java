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
import android.util.Patterns;

import java.util.Date;


public class AnadirUsuario extends AppCompatActivity {
    Api api;

    Usuario u;

    EditText txtRegistrarNombre;
    EditText txtRegistrarPass;
    EditText txtRepetirPass;
    EditText txtRegistrarCorreo;

    Button btnRegistrarUsuario;
    String nombreUsuario;
    String pass;

    String confirmarPass;

    String correoUsuario;

    boolean añadido = false;


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
        correoUsuario = "";

        api = new Api();
        u = new Usuario();

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreUsuario = txtRegistrarNombre.getText().toString().trim();
                pass = txtRegistrarPass.getText().toString().trim();
                confirmarPass = txtRepetirPass.getText().toString().trim();
                correoUsuario = txtRegistrarCorreo.getText().toString().trim();

                // Validar campos vacíos
                if (nombreUsuario.isEmpty() || pass.isEmpty() || confirmarPass.isEmpty() || correoUsuario.isEmpty()) {
                    reiniciarCampos("Todos los campos son obligatorios");
                    return;
                }

                // Validar correo
                if (!correoValido(correoUsuario)) {
                    txtRegistrarCorreo.setText("");
                    reiniciarCampos("Correo electrónico no válido");
                    return;
                }

                // Validar contraseñas
                if (!pass.equals(confirmarPass)) {
                    txtRegistrarPass.setText("");
                    txtRepetirPass.setText("");
                    reiniciarCampos("Las contraseñas no coinciden");
                    return;
                }

                // Hilo para llamadas de red
                new Thread(() -> {
                    try {
                        // Actualizar UI en el hilo principal
                        runOnUiThread(() -> {
                                // Añadir usuario
                                api.addUsuario(AnadirUsuario.this ,nombreUsuario, correoUsuario, pass, "");
                                añadido = true;

                        });

                    } catch (Exception e) {
                        // Si ocurre algún error en la red, mostrarlo con tu función
                        runOnUiThread(() -> reiniciarCampos("Error al conectar con el servidor"));
                        e.printStackTrace();
                    }
                }).start();

                System.out.println("Usuario" + nombreUsuario + pass + añadido);
                //Posible solucion --> no se ejecuta porque el usuario aun no se ha mandado a la base de datos
                if(añadido){
                    api.getUsuarioNombrePass(AnadirUsuario.this, nombreUsuario, pass, (success, usu) -> {

                        if(success){
                            u = usu;

                            // Crear usuario para enviar a MenuPrincipal
                            Usuario u = new Usuario(nombreUsuario, correoUsuario, "", null, new Date());
                            Intent intentPrincipal = new Intent(AnadirUsuario.this, MenuPrincipal.class);
                            intentPrincipal.putExtra("usuario", u);

                            startActivity(intentPrincipal);

                        }
                    });
                }
            }
        });
    }

    private boolean correoValido(String correo) {
        return correo != null && Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    public void reiniciarCampos(String datoError){

        //Lanzar mensaje de Error
        Toast toast = Toast.makeText(getApplicationContext(), datoError, Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_background);

        // Cambiar color del texto a blanco para que contraste
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);

        toast.show();
    }
}