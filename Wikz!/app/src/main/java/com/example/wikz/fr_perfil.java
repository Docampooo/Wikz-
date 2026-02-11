package com.example.wikz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class fr_perfil extends Fragment {

    //Establecer los datos del usuario

    Api api;

    Usuario u;

    ImageView ivFotoPerfil;
    TextView tvNombrePerfil;

    TextView tvDescripcionPerfil;

    Button btnEditar;

    RecyclerView rvPublicacionesPerfil;

    AdaptadorPublicaciones adaptadorPublicaciones;
    ArrayList<Publicacion> publicacionesPerfil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuPrincipal activity = (MenuPrincipal) getActivity();
        u = activity.getUsuario();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_perfil, container, false);

        ivFotoPerfil = view.findViewById(R.id.ivFotoPerfil);

        api = new Api();

        if (u != null) {
            api.getFotoPerfil(getActivity(), u.getId(), bitmap -> {
                // isAdded() comprueba que el fragmento sigue en pantalla
                if (isAdded() && bitmap != null) {
                    ivFotoPerfil.setImageBitmap(bitmap);
                }
            });
        }

        tvNombrePerfil = view.findViewById(R.id.tvNombrePerfil);
        tvNombrePerfil.setText(u.getNombre());

        btnEditar = view.findViewById(R.id.btnEditarPerfil);

        btnEditar.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), EditarPerfil.class);
            intent.putExtra("usuario", u);
            startActivityForResult(intent, 200);
        });


        tvDescripcionPerfil = view.findViewById(R.id.tvDescripcionPerfil);

        tvDescripcionPerfil.setText(u.getBiografia());

        //Rv Publicaciones
        //Añadir publicaciones para probar el codigo
        publicacionesPerfil = new ArrayList<>();

        adaptadorPublicaciones = new AdaptadorPublicaciones(publicacionesPerfil);
        rvPublicacionesPerfil = view.findViewById(R.id.rvPublicacionesPerfil);
        rvPublicacionesPerfil.setAdapter(adaptadorPublicaciones);

        GridLayoutManager gridVertical =new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        rvPublicacionesPerfil.setLayoutManager(gridVertical);

        //Añadir las publicaciones al recyclerView
        api.getPublicacionesUsuario(fr_perfil.this.getActivity(), u.getId(), (success, pubs) -> {
            if(success){

                publicacionesPerfil.clear();
                publicacionesPerfil.addAll(pubs);
                adaptadorPublicaciones.notifyDataSetChanged();

            } else {
                fr_perfil.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error Cargando las publicaciones del usuario", Toast.LENGTH_SHORT).show();
                });
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {

            Usuario actualizado = (Usuario) data.getSerializableExtra("usuario");

            //actualizar usuario GLOBAL
            MenuPrincipal activity = (MenuPrincipal) getActivity();
            activity.setUsuario(actualizado);

            u = actualizado;

            tvNombrePerfil.setText(u.getNombre());
            tvDescripcionPerfil.setText(u.getBiografia());

            api.getFotoPerfil(getActivity(), u.getId(), bitmap -> {
                if (isAdded() && bitmap != null) {
                    ivFotoPerfil.setImageBitmap(bitmap);
                }
            });
        }
    }
}