package clases;

import java.util.ArrayList;

public class Coleccion {

     public Coleccion(int id_usuario, String nombre, int elementos, byte[] imagen) {
        setTitulo(nombre);
        setElementos(elementos);
        setImagen(imagen);
    }

    public Coleccion() {
        this(0, "", 0, null);
    }

    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    private int id;

    public int getId() {
        return id;
    }

    private int id_usuario;

    public void setIdUsuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    public int getIdUsuario(){
        return id_usuario;
    }

    private int elementos;

    public void setElementos(int elementos) {
        this.elementos = elementos;
    }

    public int getElementos() {
        return elementos;
    }

    private byte[] imagen;

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public byte[] getImagen() {
        return imagen;
    }
}
