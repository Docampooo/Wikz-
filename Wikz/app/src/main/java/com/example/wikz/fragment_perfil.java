package com.example.wikz;

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

import java.util.ArrayList;


public class fragment_perfil extends Fragment {

    //Establecer los datos del usuario
    ImageView ivFotoPerfil;
    TextView tvNombrePerfil;

    TextView tvDescripcionPerfil;

    Button btnEditar;

    RecyclerView rvPublicacionesPerfil;

    AdaptadorPublicaciones adaptadorPublicaciones;
    ArrayList<Publicacion> publicacionesPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_perfil, container, false);

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        ivFotoPerfil = view.findViewById(R.id.ivFotoPerfil);

        tvNombrePerfil = view.findViewById(R.id.tvNombrePerfil);

        btnEditar = view.findViewById(R.id.btnEditarPerfil);

        tvDescripcionPerfil = view.findViewById(R.id.tvDescripcionPerfil);

        //Rv Publicaciones
        //Añadir publicaciones para probar el codigo
        publicacionesPerfil = Publicacion.establecerPublicaciones();

        adaptadorPublicaciones = new AdaptadorPublicaciones(publicacionesPerfil);
        rvPublicacionesPerfil = view.findViewById(R.id.rvPublicacionesPerfil);
        rvPublicacionesPerfil.setAdapter(adaptadorPublicaciones);

        GridLayoutManager gridVertical =new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        rvPublicacionesPerfil.setLayoutManager(gridVertical);

        return view;
    }
}