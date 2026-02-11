package com.example.wikz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class fr_publicar extends Fragment {
    Api api;
    Usuario u;
    EditText txtTitulo;

    EditText txtDescripcion;

    ImageView ivImagenPublicacion;

    Button btnSubir;

    Bitmap imagenSeleccionada;


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
        View view = inflater.inflate(R.layout.fr_publicar, container, false);

        api = new Api();

        txtTitulo = view.findViewById(R.id.txtTitulo);

        txtDescripcion = view.findViewById(R.id.txtDescripcion);

        ivImagenPublicacion = view.findViewById(R.id.ivImagenPublicar);

        btnSubir = view.findViewById(R.id.btnPublicar);

        ivImagenPublicacion.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivityForResult(intent, 101);
            }
        });

        btnSubir.setOnClickListener(v -> {

            String titulo = txtTitulo.getText().toString().trim();
            String descripcion = txtDescripcion.getText().toString().trim();

            //Comprobar título
            if (titulo.isEmpty()) {
                txtTitulo.setError("El título es obligatorio");
                txtTitulo.requestFocus();
                return;
            }

            //Comprobar imagen
            if (imagenSeleccionada == null) {
                Toast.makeText(
                        requireContext(),
                        "Debes seleccionar una imagen para la publicación",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            //Descripción puede estar vacía (no se valida)
            if (descripcion.isEmpty()) {
                descripcion = "";
            }

            Publicacion p = new Publicacion();
            p.setTitulo(titulo);
            p.setDescripcion(descripcion);
            p.setIdUsuario(u.getId());

            api.addPublicacion(requireActivity(), u.getId(), titulo, imagenSeleccionada, descripcion, success -> {

                        if (success) {
                            Toast.makeText(
                                    getContext(),
                                    "Imagen publicada!",
                                    Toast.LENGTH_SHORT
                            ).show();

                            txtDescripcion.setText("");
                            txtTitulo.setText("");
                            ivImagenPublicacion.setImageBitmap(null);

                        } else {
                            Toast.makeText(
                                    getContext(),
                                    "Error al subir la publicación",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null) {

            try {
                Uri imageUri = data.getData();

                Bitmap bitmap = BitmapFactory.decodeStream(
                        requireActivity()
                                .getContentResolver()
                                .openInputStream(imageUri)
                );

                //AQUÍ SE GUARDA
                imagenSeleccionada = bitmap;

                // Mostrar preview
                ivImagenPublicacion.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(
                        requireContext(),
                        "Error al cargar la imagen",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}