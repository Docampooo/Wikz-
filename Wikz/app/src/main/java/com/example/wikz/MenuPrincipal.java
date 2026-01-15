package com.example.wikz;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {

    //RecyclerView de las colecciones del usuario
    ArrayList<ColeccionUsuario> colecciones;

    RecyclerView rvColecciones;
    AdaptadorColecciones adaptadorColecciones;
    RecyclerView rvPublicaciones;
    BottomNavigationView navContenidoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        colecciones = new ArrayList<>();
        //Añadir unas colecciones para probar el programa
        colecciones.add(new ColeccionUsuario("lvlRandom", 0, 0, R.drawable.lain));
        colecciones.add(new ColeccionUsuario("MetalVibes", 1, 0, R.drawable.chino));
        colecciones.add(new ColeccionUsuario("luv:3", 0, 0, R.drawable.beso));
        colecciones.add(new ColeccionUsuario("trivs", 0, 0, R.drawable.cruz));
        colecciones.add(new ColeccionUsuario("tunning!", 0, 0, R.drawable.zapas_korn));

        adaptadorColecciones = new AdaptadorColecciones(colecciones);
        rvColecciones = findViewById(R.id.rvColecciones);

        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvColecciones.setLayoutManager(horizontal);
        rvColecciones.setAdapter(adaptadorColecciones);
    }
}