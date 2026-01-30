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
import java.util.List;

public class fragment_explorar extends Fragment {

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
        if (getArguments() != null) {
            u = (Usuario) getArguments().getSerializable("usuario");
        }
    }

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

        api = new Api();

        tvTematica.getPaint().setShader(shader);

        publicacionesExplorar = new ArrayList<>();

        rvExplorar = view.findViewById(R.id.rvExplorar);

        adaptadorPublicaciones = new AdaptadorPublicaciones(publicacionesExplorar);
        rvExplorar.setAdapter(adaptadorPublicaciones);

        GridLayoutManager gridVertical =new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        rvExplorar.setLayoutManager(gridVertical);


        new Thread(() -> {
            ArrayList<Publicacion> res = api.getPublicaciones(requireActivity());

            requireActivity().runOnUiThread(() -> {
                if(res != null){
                    publicacionesExplorar.clear();
                    publicacionesExplorar.addAll(res);
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


        return view;
    }
}