package com.example.wikz;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorPublicaciones extends  RecyclerView.Adapter<AdaptadorPublicaciones.MyViewHolder>{
    ArrayList<Publicacion> publicaciones;

    Api api = new Api();

    //Constructor
    public AdaptadorPublicaciones(ArrayList<Publicacion> publicaciones){
        this.publicaciones = publicaciones;

    }

    public AdaptadorPublicaciones.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_publicacion,
                parent, false);
        AdaptadorPublicaciones.MyViewHolder mvh = new AdaptadorPublicaciones.MyViewHolder(elemento);

        return mvh ;

    }

    public void onBindViewHolder(@NonNull AdaptadorPublicaciones.MyViewHolder holder, int position) {
        Publicacion pu = this.publicaciones.get(position);

        // Configuración inicial de la celda
        holder.imagenPublicacion.setImageResource(R.drawable.fotoperfil);

        api.getFotoPublicacion((Activity) holder.itemView.getContext(), pu.getId(), bitmap -> {
            if (bitmap != null) {
                holder.imagenPublicacion.setImageBitmap(bitmap);
            }
        });

        // Gestión del color de selección
        holder.itemView.setBackgroundResource(selectedPos == position ? R.color.moradoLogo : R.color.white);

        // CUANDO HAGAS CLIC, NO USES "position"
        holder.itemView.setOnClickListener(v -> {
            // Guardamos la posición antigua para refrescarla
            int oldPos = selectedPos;

            // ¡AQUÍ USAMOS holder.getAdapterPosition()!
            selectedPos = holder.getAdapterPosition();

            // Notificamos los cambios para que se repinten solo esas dos celdas
            notifyItemChanged(oldPos);
            notifyItemChanged(selectedPos);
        });
    }

    public int getItemCount() {
        return this.publicaciones.size();
    }

    int selectedPos= RecyclerView.NO_POSITION;
    public int getSelectedPos() {
        return selectedPos;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagenPublicacion;

        public MyViewHolder(View viewElemento){
            super(viewElemento);

            //añadir la celda
            this.imagenPublicacion = viewElemento.findViewById(R.id.imagenPublicacion);
        }

        public ImageView getImagenPublicacion(){
            return  imagenPublicacion;
        }
    }
}
