package com.example.wikz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class fr_contenido extends Fragment {
    Api api;

    Usuario u;

    ArrayList<Publicacion> publicaciones;

    ArrayList<Coleccion> colecciones;
    RecyclerView rvColecciones;
    AdaptadorColecciones adaptadorColecciones;

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
        View view = inflater.inflate(R.layout.fr_contenido, container, false);

        api = new Api();

        //Rv Publicaciones
        publicaciones = new ArrayList<>();

        rvPublicaciones = view.findViewById(R.id.rvPublicaciones);
        adaptadorPublicaciones = new AdaptadorPublicaciones(publicaciones);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 3);
        rvPublicaciones.setLayoutManager(grid);
        rvPublicaciones.setAdapter(adaptadorPublicaciones);


        //Añadir las publicaciones al recyclerView
        api.getPublicaciones(fr_contenido.this.getActivity(), (success, pubs) -> {

            if(success){

                publicaciones.clear();
                publicaciones.addAll(pubs);
                adaptadorPublicaciones.notifyDataSetChanged();

            } else {
                fr_contenido.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error Cargando las publicaciones", Toast.LENGTH_SHORT).show();
                });
            }
        });

        //Rv Colecciones
        colecciones = new ArrayList<>();

        adaptadorColecciones = new AdaptadorColecciones(colecciones);
        rvColecciones = view.findViewById(R.id.rvColecciones);

        LinearLayoutManager horizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvColecciones.setLayoutManager(horizontal);
        rvColecciones.setAdapter(adaptadorColecciones);

        api.getColeccionesUsuario(fr_contenido.this.getActivity(), u.getId(), (success, colecs) -> {
            if(success){

                colecciones.clear();
                colecciones.addAll(colecs);
                adaptadorColecciones.notifyDataSetChanged();

            } else {
                fr_contenido.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error Cargando las colecciones del usuario", Toast.LENGTH_SHORT).show();
                });
            }
        });

        return view;
    }
}