package com.example.wikz;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Coleccion {

    public Coleccion(int idUsuario, String titulo, ArrayList<Publicacion> elementos, Bitmap imagen) {

        setIdUsuario(idUsuario);
        setTitulo(titulo);
        setElementos(elementos);
        setImagen(imagen);
    }

    public Coleccion() {
        this(0, "", new ArrayList<Publicacion>(), null);
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private int idUsuario;

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    private ArrayList<Publicacion> elementos;

    public void setElementos(ArrayList<Publicacion> elementos) {
        this.elementos = elementos;
    }

    public ArrayList<Publicacion> getElementos() {
        return elementos;
    }

    private Bitmap imagenBase64;

    public void setImagen(Bitmap imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Bitmap getImagen() {
        return imagenBase64;
    }

    @Override
    public String toString() {
        return "Coleccion{id=" + id +
                ", titulo=" + titulo +
                ", elementos=" + elementos.size() +
                ", idUsuario=" + idUsuario + "}";
    }
}

