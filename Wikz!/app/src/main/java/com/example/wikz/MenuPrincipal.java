package com.example.wikz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity {


    Intent intentUsuario;
    BottomNavigationView navMenu;

    fragment_home frHome;
    fragment_explorar frExp;

    fragment_perfil frPerf;

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

        intentUsuario = getIntent();

        Usuario u = (Usuario)intentUsuario.getSerializableExtra("usuario");

        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", u);
        Log.d("Mostremos el usuario enviado a ver que tieneeeee",  String.format("%s %d", u.getNombre(), u.getId()));

        navMenu = findViewById(R.id.navMenu);

        frHome = new fragment_home();
        frHome.setArguments(bundle);

        frExp = new fragment_explorar();
        frExp.setArguments(bundle);

        frPerf = new fragment_perfil();
        frPerf.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                )
                .replace(R.id.fragment_container, frHome)
                .commit();

        navMenu.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if(id == R.id.mn_inicio){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, frHome)
                        .commit();
                return true;

            }else if( id == R.id.mn_explorar){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, frExp)
                        .commit();
                return true;

            }else if(id == R.id.mn_perfil){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, frPerf)
                        .commit();
                return true;

            }else{
                return false;
            }
        });
    }
}