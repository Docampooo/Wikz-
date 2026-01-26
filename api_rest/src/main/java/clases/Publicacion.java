package clases;

import java.util.ArrayList;
import java.util.Date;

public class Publicacion {

    public Publicacion(int id_usuario, String nombre, byte[] imagen, String descripcion) {

        setIdUsuario(id_usuario);
        setNombre(nombre);
        setImagen(imagen);
        setDescripcion(descripcion);

        setFechaCreacion(new Date());
    }

    private String nombre; // Nombre de la coleccion

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String descripcion;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    private int id; // Id de la colección

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
