package clases;

import java.util.ArrayList;

public class Coleccion {

     public Coleccion(int id_usuario, String nombre, int elementos, byte[] imagen) {
        setNombre(nombre);
        setElementos(elementos);
        setImagen(imagen);
    }

    public Coleccion() {
        this(0, "", 0, null);
    }

    private String nombre;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
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
