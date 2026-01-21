package com.example.wikz;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity {

    BottomNavigationView navMenu;

    fragment_home frHome = new fragment_home();

    fragment_explorar frMsg = new fragment_explorar();

    fragment_perfil frPerf = new fragment_perfil();

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

        navMenu = findViewById(R.id.navMenu);

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
                        .replace(R.id.fragment_container, frMsg)
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