package com.example.wikz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class fragment_home extends Fragment {


    //RecyclerView de las colecciones del usuario
    ArrayList<Coleccion> colecciones;
    ArrayList<Publicacion> publicaciones;

    RecyclerView rvColecciones;
    AdaptadorColecciones adaptadorColecciones;
    AdaptadorPublicaciones adaptadorPublicaciones;
    RecyclerView rvPublicaciones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Rv colecciones
        colecciones = Coleccion.obtenerColecciones();

        adaptadorColecciones = new AdaptadorColecciones(colecciones);
        rvColecciones = view.findViewById(R.id.rvColecciones);

        LinearLayoutManager horizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvColecciones.setLayoutManager(horizontal);
        rvColecciones.setAdapter(adaptadorColecciones);

        //Rv Publicaciones
        //Añadir publicaciones para probar el codigo
        publicaciones = Publicacion.establecerPublicaciones();

        adaptadorPublicaciones = new AdaptadorPublicaciones(publicaciones);
        rvPublicaciones = view.findViewById(R.id.rvPublicaciones);
        rvPublicaciones.setAdapter(adaptadorPublicaciones);

        GridLayoutManager gridVertical =new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        rvPublicaciones.setLayoutManager(gridVertical);

        return view;
    }
}