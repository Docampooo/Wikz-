package com.example.wikz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class fragment_home extends Fragment {


    Api api;

    Usuario u;
    ArrayList<Coleccion> colecciones;
    RecyclerView rvColecciones;
    AdaptadorColecciones adaptadorColecciones;

    ArrayList<Publicacion> publicaciones;
    AdaptadorPublicaciones adaptadorPublicaciones;
    RecyclerView rvPublicaciones;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            u = (Usuario) getArguments().getSerializable("usuario");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        api = new Api();

        //Rv Publicaciones
        publicaciones = new ArrayList<>();

        rvPublicaciones = view.findViewById(R.id.rvPublicaciones);
        adaptadorPublicaciones = new AdaptadorPublicaciones(publicaciones);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 3);
        rvPublicaciones.setLayoutManager(grid);
        rvPublicaciones.setAdapter(adaptadorPublicaciones);

        new Thread(() -> {
            ArrayList<Publicacion> res = api.getPublicaciones(requireActivity());

            requireActivity().runOnUiThread(() -> {
                if(res != null){
                    publicaciones.clear();
                    publicaciones.addAll(res);
                    adaptadorPublicaciones.notifyDataSetChanged();
                } else {
                    Toast.makeText(
                            getContext(),
                            "Error cargando publicaciones",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }).start();

        //Rv Colecciones
        colecciones = new ArrayList<>();


        adaptadorColecciones = new AdaptadorColecciones(colecciones);
        rvColecciones = view.findViewById(R.id.rvColecciones);

        LinearLayoutManager horizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvColecciones.setLayoutManager(horizontal);
        rvColecciones.setAdapter(adaptadorColecciones);

        new Thread(() -> {
            ArrayList<Coleccion> res = api.getColeccionesUsuario(u.getId());

            requireActivity().runOnUiThread(() -> {
                if(res != null){
                    colecciones.clear();
                    colecciones.addAll(res);
                    adaptadorColecciones.notifyDataSetChanged();
                } else {
                    Toast.makeText(
                            getContext(),
                            "Error cargando colecciones",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }).start();

        return view;
    }
}