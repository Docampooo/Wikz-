package com.example.wikz;

import java.util.ArrayList;

public class Coleccion {

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

    public Coleccion(String nombre, int id, int elementos, int imagen){
        setNombre(nombre);
        setId(id);
        setElementos(elementos);
        setImagen(imagen);
    }

    public Coleccion(){
        this("", 0, 0, 0);
    }

    public static ArrayList<Coleccion> obtenerColecciones(){

        ArrayList<Coleccion> colecciones = new ArrayList<>();

        //Añadir unas colecciones para probar el programa
        colecciones.add(new Coleccion("lvlRandom", 0, 0, R.drawable.lain));
        colecciones.add(new Coleccion("MetalVibes", 1, 0, R.drawable.chino));
        colecciones.add(new Coleccion("luv:3", 2, 0, R.drawable.beso));
        colecciones.add(new Coleccion("trivs", 3, 0, R.drawable.cruz));
        colecciones.add(new Coleccion("tunning!", 4, 0, R.drawable.zapas_korn));
        colecciones.add(new Coleccion("Editzz", 5, 0, R.drawable.evanescense));
        colecciones.add(new Coleccion("HardPics", 6, 0, R.drawable.minecraft));

        return  colecciones;
    }

    public static ArrayList<Coleccion> obtenerTematicas(){

        ArrayList<Coleccion> tematicas = new ArrayList<>();

        //Añadir unas tematicas para probar el programa
        tematicas.add(new Coleccion("NuMetal", 0, 0,R.drawable.numetal));
        tematicas.add(new Coleccion("Punk", 1,  0,R.drawable.punk));
        tematicas.add(new Coleccion("Rock", 2, 0,R.drawable.rock));
        tematicas.add(new Coleccion("Hippie", 3, 0,R.drawable.hippie));
        tematicas.add(new Coleccion("HyperPop", 4, 0,R.drawable.hyperpop));
        tematicas.add(new Coleccion("HeavyMetal", 5, 0,R.drawable.heavymetal));

        return  tematicas;
    }
}


