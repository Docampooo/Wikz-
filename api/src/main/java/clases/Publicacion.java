package clases;

import java.util.ArrayList;
import java.util.Date;

public class Publicacion {

    public Publicacion(int id, int id_usuario, String titulo, byte[] imagen, String descripcion) {

        setId(id);
        setIdUsuario(id_usuario);
        setTitulo(titulo);
        setImagen(imagen);
        setDescripcion(descripcion);

        setFechaCreacion(new Date());
    }

    public Publicacion(){
        this(0, 0, "", null, "");
    }

    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    private String descripcion;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    private int id; // Id de la colección

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private int id_usuario;

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    private byte[] imagen; // Imagen de la coleccion

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    private Date creacionPublicacion;

    public void setFechaCreacion(Date fechaCreacion) {
        this.creacionPublicacion = fechaCreacion;
    }

    public Date getFechaCreacion() {
        return creacionPublicacion;
    }
}
