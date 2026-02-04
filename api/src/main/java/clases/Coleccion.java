package clases;

import java.util.ArrayList;

public class Coleccion {

    public Coleccion(int idUsuario, String titulo, String imagenBase64) {

        setIdUsuario(idUsuario);
        setTitulo(titulo);
        setImagenBase64(imagenBase64);
    }

    public Coleccion() {
        this(0, "", null);
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

    private String imagenBase64;

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    @Override
    public String toString() {
        return "Coleccion{id=" + id +
                ", titulo=" + titulo +
                ", idUsuario=" + idUsuario + "}";
    }
}
