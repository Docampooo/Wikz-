package com.example.wikz;

public class ColeccionUsuario {

    private String nombre; //Nombre de la coleccion

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    private int id; //Id de la colección
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return  id;
    }
    private int elementos; //total de elementos de la coleccion
    public void setElementos(int elementos) {
        this.elementos = elementos;
    }
    public int getElementos() {
        return  elementos;
    }
    private int imagen; //Imagen de la coleccion
    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
    public int getImagen() {
        return  imagen;
    }

    public ColeccionUsuario(String nombre, int id, int elementos, int imagen){
        setNombre(nombre);
        setId(id);
        setElementos(elementos);
        setImagen(imagen);
    }

    public  ColeccionUsuario(){
        this("", 0, 0, 0);
    }
}
