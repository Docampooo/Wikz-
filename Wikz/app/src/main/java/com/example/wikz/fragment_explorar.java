package com.example.wikz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class fragment_explorar extends Fragment {

    TextView tvTematica;

    ImageView ivTematica;

    Bitmap bitmap;

    SearchView buscador;

    RecyclerView rvExplorar;

    AdaptadorPublicaciones adaptadorPublicaciones;

    ArrayList<Publicacion> publicacionesExplorar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_explorar, container, false);

        View view = inflater.inflate(R.layout.fragment_explorar, container, false);

        tvTematica = view.findViewById(R.id.tvTematica);
        ivTematica = view.findViewById(R.id.ivTematica);

        //cambiar dependiendo de la tematica vigente
        bitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.numetal);

        Shader shader = new BitmapShader(
                bitmap,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
        );

        tvTematica.getPaint().setShader(shader);

        publicacionesExplorar = Publicacion.establecerPublicaciones();

        rvExplorar = view.findViewById(R.id.rvExplorar);

        adaptadorPublicaciones = new AdaptadorPublicaciones(publicacionesExplorar);
        rvExplorar.setAdapter(adaptadorPublicaciones);

        GridLayoutManager gridVertical =new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        rvExplorar.setLayoutManager(gridVertical);

        return view;
    }
}