package clases;

import java.util.ArrayList;
import java.util.Date;

public class Publicacion {

    public Publicacion(String titulo, String imagenBase64, String descripcion, Date fechaCreacion) {

        this.titulo = titulo != null ? titulo : "";
        this.imagenBase64 = imagenBase64;
        this.descripcion = descripcion != null ? descripcion : "";
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : new Date();
    }

    public Publicacion() {
        this("", null, "", new Date());
    }

    private int id;
    private int id_usuario;
    private String titulo;
    private String descripcion;
    private String imagenBase64;
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

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Publicacion{id=" + id + ", id_usuario=" + id_usuario + ", titulo=" + titulo + "}";
    }
}
