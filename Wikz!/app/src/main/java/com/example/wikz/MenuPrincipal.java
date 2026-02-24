package com.example.wikz;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity {

    public Usuario uPrincipal;
    public Usuario getUsuario() { return uPrincipal; }
    public void setUsuario(Usuario u) { this.uPrincipal = u; }

    Intent intentUsuario;
    BottomNavigationView navMenu;
    fr_publicar frHome;
    fr_content frExp;
    fr_perfil frPerf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mantenemos EdgeToEdge pero limitamos su impacto en el Bottom
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_principal);

        navMenu = findViewById(R.id.navMenu);
        navMenu.setItemIconTintList(null);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Resto de tu lógica de fragmentos...
        intentUsuario = getIntent();
        uPrincipal = (Usuario) intentUsuario.getSerializableExtra("usuario");
        frHome = new fr_publicar();
        frExp = new fr_content();
        frPerf = new fr_perfil();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frHome).commit();

        navMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.mn_publicar) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frHome).commit();
                return true;
            } else if (id == R.id.mn_contenido) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frExp).commit();
                return true;
            } else if (id == R.id.mn_perfil) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frPerf).commit();
                return true;
            }
            return false;
        });
    }
}