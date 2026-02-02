package com.example.wikz;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Publicacion implements Serializable {

    public Publicacion(int id_usuario, String titulo, Bitmap imagenBase64, String descripcion, Date fechaCreacion) {
        this.id_usuario = id_usuario;
        this.titulo = titulo != null ? titulo : "";
        this.imagen = imagenBase64;
        this.descripcion = descripcion != null ? descripcion : "";
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : new Date();
    }

    public Publicacion() {
        this(0, "", null, "", new Date());
    }

    private int id;
    private int id_usuario;
    private String titulo;
    private String descripcion;
    private Bitmap imagen;
    private Date fechaCreacion;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Publicacion{id=" + id + ", id_usuario=" + id_usuario + ", titulo=" + titulo + "}";
    }
}

