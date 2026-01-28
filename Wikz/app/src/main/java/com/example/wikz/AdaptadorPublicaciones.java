package com.example.wikz;

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

        if (selectedPos == position)
            holder.itemView.setBackgroundResource(R.color.moradoLogo);
        else holder.itemView.setBackgroundResource(R.color.white);
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
