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
import android.widget.Toast;

import java.util.ArrayList;

public class fr_content extends Fragment {

    Api api;

    Usuario u;
    TextView tvTematica;

    ImageView ivTematica;

    Bitmap bitmap;

    SearchView buscador;

    RecyclerView rvExplorar;

    AdaptadorPublicaciones adaptadorPublicaciones;

    ArrayList<Publicacion> publicacionesExplorar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuPrincipal activity = (MenuPrincipal) getActivity();
        u = activity.getUsuario();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fr_content, container, false);

        View view = inflater.inflate(R.layout.fr_content, container, false);

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

        api = new Api();

        tvTematica.getPaint().setShader(shader);

        publicacionesExplorar = new ArrayList<>();

        rvExplorar = view.findViewById(R.id.rvExplorar);

        adaptadorPublicaciones = new AdaptadorPublicaciones(publicacionesExplorar);
        rvExplorar.setAdapter(adaptadorPublicaciones);

        GridLayoutManager gridVertical =new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        rvExplorar.setLayoutManager(gridVertical);

        //Añadir las publicaciones al recyclerView
        api.getPublicaciones(fr_content.this.getActivity(), (success, pubs) -> {
            if(success){

                publicacionesExplorar.clear();
                publicacionesExplorar.addAll(pubs);
                adaptadorPublicaciones.notifyDataSetChanged();

            } else {
                fr_content.this.getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error Cargando las publicaciones", Toast.LENGTH_SHORT).show();
                });
            }
        });

        return view;
    }
}