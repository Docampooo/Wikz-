package com.example.wikz;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class AdaptadorPublicaciones extends RecyclerView.Adapter<AdaptadorPublicaciones.MyViewHolder> {

    private ArrayList<Publicacion> publicaciones;
    private Api api = new Api();
    private int selectedPos = RecyclerView.NO_POSITION;
    private int idUsuarioLogueado; // Almacena el ID del usuario que usa la app

    // Constructor actualizado: recibe la lista y el ID del usuario actual
    public AdaptadorPublicaciones(ArrayList<Publicacion> publicaciones, int idUsuarioLogueado) {
        this.publicaciones = publicaciones;
        this.idUsuarioLogueado = idUsuarioLogueado;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_publicacion, parent, false);
        return new MyViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Publicacion pu = this.publicaciones.get(position);

        // 1. DATOS BÁSICOS DE LA CELDA
        holder.txtTitulo.setText(pu.getTitulo());
        holder.txtDescripcion.setText(pu.getDescripcion());

        Date fecha = pu.getFechaCreacion();
        if (fecha != null) {
            CharSequence fechaFormateada = DateFormat.format("dd/MM/yyyy HH:mm", fecha);
            holder.txtFecha.setText(fechaFormateada);
        }

        // 2. CARGA DE IMAGEN DE LA PUBLICACIÓN
        holder.imagenPublicacion.setImageResource(R.drawable.fotoperfil); // Placeholder
        api.getFotoPublicacion((Activity) holder.itemView.getContext(), pu.getId(), bitmap -> {
            if (bitmap != null) {
                holder.imagenPublicacion.setImageBitmap(bitmap);
            }
        });

        // 3. ESTILO DE SELECCIÓN
        if (selectedPos == position) {
            holder.itemView.setBackgroundResource(R.color.moradoLogo);
        } else {
            holder.itemView.setBackgroundResource(android.R.color.transparent);
        }

        // 4. CLIC PARA VER DETALLE Y BORRAR
        holder.itemView.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_detalle_publicacion, null);

            ImageView ivFotoPerfil = dialogView.findViewById(R.id.ivDetalleFotoPerfil);
            TextView tvNombre = dialogView.findViewById(R.id.tvDetalleNombreUsuario);
            ImageView ivImagenPost = dialogView.findViewById(R.id.ivDetalleImagenPost);
            TextView tvTitulo = dialogView.findViewById(R.id.tvDetalleTitulo);
            TextView tvDesc = dialogView.findViewById(R.id.tvDetalleDescripcion);
            TextView tvFecha = dialogView.findViewById(R.id.tvDetalleFecha);
            Button btnEliminar = dialogView.findViewById(R.id.btnEliminarPublicacion); // Botón de tu XML

            // Rellenar datos del diálogo
            tvTitulo.setText(pu.getTitulo());
            tvDesc.setText(pu.getDescripcion());
            if (pu.getFechaCreacion() != null) {
                tvFecha.setText(DateFormat.format("dd/MM/yyyy HH:mm", pu.getFechaCreacion()));
            }

            // Cargar imagen principal en el detalle
            api.getFotoPublicacion((Activity) v.getContext(), pu.getId(), bitmap -> {
                if (bitmap != null) ivImagenPost.setImageBitmap(bitmap);
            });

            // LÓGICA DE BORRADO: Solo si el autor es el usuario logueado
            if (pu.getIdUsuario() == idUsuarioLogueado) {
                btnEliminar.setVisibility(View.VISIBLE);
            } else {
                btnEliminar.setVisibility(View.GONE);
            }

            // Crear el diálogo antes para poder cerrarlo desde el botón
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
            builder.setView(dialogView);
            android.app.AlertDialog dialog = builder.create();

            // Configurar el botón de eliminar
            btnEliminar.setOnClickListener(v1 -> {
                new android.app.AlertDialog.Builder(v.getContext())
                        .setTitle("¿Eliminar publicación?")
                        .setMessage("Esta acción quitará la publicación de Wikz permanentemente.")
                        .setPositiveButton("Eliminar", (d, i) -> {
                            api.eliminarPublicacion((Activity) v.getContext(), pu.getId(), success -> {
                                if (success) {
                                    // Borrar de la lista y animar la salida
                                    int currentPos = holder.getAdapterPosition();
                                    publicaciones.remove(currentPos);
                                    notifyItemRemoved(currentPos);
                                    notifyItemRangeChanged(currentPos, publicaciones.size());
                                    dialog.dismiss();
                                    Toast.makeText(v.getContext(), "Publicación eliminada", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(v.getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            });

            // Cargar datos del autor (Nombre y Foto de perfil)
            api.getUsuarioId((Activity) v.getContext(), pu.getIdUsuario(), (success, usuario) -> {
                if (success && usuario != null) {
                    tvNombre.setText(usuario.getNombre());
                    api.getFotoPerfil((Activity) v.getContext(), usuario.getId(), bmpPerfil -> {
                        if (bmpPerfil != null) ivFotoPerfil.setImageBitmap(bmpPerfil);
                    });
                }
            });

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return (this.publicaciones != null) ? this.publicaciones.size() : 0;
    }

    // ViewHolder: Conexión con los IDs de celda_publicacion.xml
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenPublicacion;
        TextView txtTitulo, txtDescripcion, txtFecha;

        public MyViewHolder(View viewElemento) {
            super(viewElemento);
            this.imagenPublicacion = viewElemento.findViewById(R.id.imagenPublicacion);
            this.txtTitulo = viewElemento.findViewById(R.id.txtTitulo);
            this.txtDescripcion = viewElemento.findViewById(R.id.txtDescripcion);
            this.txtFecha = viewElemento.findViewById(R.id.txtFecha);
        }
    }
}