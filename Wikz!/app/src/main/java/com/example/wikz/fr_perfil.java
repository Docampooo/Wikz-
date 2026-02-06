package com.example.wikz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        if (getArguments() != null) {
            u = (Usuario) getArguments().getSerializable("usuario");

        }
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
                    u.setFotoPerfil(bitmap);
                }
            });
        }

        tvNombrePerfil = view.findViewById(R.id.tvNombrePerfil);
        tvNombrePerfil.setText(u.getNombre());

        btnEditar = view.findViewById(R.id.btnEditarPerfil);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditarPerfil.class);

                u.setFotoPerfil(null);
                intent.putExtra("usuario", u);
                startActivityForResult(intent, 200);
            }
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

        if (requestCode == 200 && resultCode == getActivity().RESULT_OK && data != null) {
            // Recibimos los nuevos textos (nombre, bio)
            u = (Usuario) data.getSerializableExtra("usuario");

            tvNombrePerfil.setText(u.getNombre());
            tvDescripcionPerfil.setText(u.getBiografia());

            // Volvemos a pedir la foto a la API para refrescar
            api.getFotoPerfil(getActivity(), u.getId(), bitmap -> {
                if (isAdded() && bitmap != null) {
                    ivFotoPerfil.setImageBitmap(bitmap);
                }
            });
        }
    }
}