package com.example.wikz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorColecciones extends  RecyclerView.Adapter<AdaptadorColecciones.MyViewHolder>{

    ArrayList<Coleccion> coleccionesUsuario;

    //Constructor
    public AdaptadorColecciones(ArrayList<Coleccion> coleccionesUsuarios){
        this.coleccionesUsuario = coleccionesUsuarios;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_coleccion,
                parent, false);
        MyViewHolder mvh = new MyViewHolder(elemento);
        return mvh ;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Coleccion cu = this.coleccionesUsuario.get(position);

        holder.getNombreColeccion().setText(cu.getNombre());
        holder.getImagenColeccion().setImageResource(cu.getImagen());

        if (selectedPos == position)
            holder.itemView.setBackgroundResource(R.color.moradoLogo);
        else holder.itemView.setBackgroundResource(R.color.white);
    }

    @Override
    public int getItemCount() {
        return this.coleccionesUsuario.size();
    }

    int selectedPos=RecyclerView.NO_POSITION;
    public int getSelectedPos() {
        return selectedPos;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreColeccion;
        ImageView imagenColeccion;

        public MyViewHolder(View viewElemento){
            super(viewElemento);

            //añadir la celda
            this.nombreColeccion = viewElemento.findViewById(R.id.nombreColeccion);
            this.imagenColeccion = viewElemento.findViewById(R.id.imagenColeccion);
        }

        public TextView getNombreColeccion(){
            return  nombreColeccion;
        }

        public ImageView getImagenColeccion(){
            return  imagenColeccion;
        }
    }
}
