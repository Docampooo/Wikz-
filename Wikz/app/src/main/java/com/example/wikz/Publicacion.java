package com.example.wikz;

import java.util.ArrayList;
import java.util.Date;

public class Publicacion {

    private String nombre; //Nombre de la coleccion

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public String descripcion;
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    private int id; //Id de la colección
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return  id;
    }
    private int imagen; //Imagen de la coleccion
    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
    public int getImagen() {
        return  imagen;
    }

    private Date creacionPublicacion;
    public void setFechaCreacion(Date fechaCreacion) {
        this.creacionPublicacion = fechaCreacion;
    }

    public Date getFechaCreacion() {
        return creacionPublicacion;
    }

    public Publicacion(String nombre, int id, int imagen, String descripcion){
        setNombre(nombre);
        setId(id);
        setImagen(imagen);
        setDescripcion(descripcion);
        setFechaCreacion(new Date());
    }

    public static ArrayList<Publicacion> establecerPublicaciones(){

        ArrayList<Publicacion> publicaciones = new ArrayList<>();

        publicaciones.add(new Publicacion("Colegas!", 0, R.drawable.colegas, "Foton con los colegas"));
        publicaciones.add(new Publicacion("Mega Monster", 1, R.drawable.monster, "imagen durisima de monster"));
        publicaciones.add(new Publicacion("swagger fit", 2, R.drawable.outfit, "Outfit mu mu chulo"));
        publicaciones.add(new Publicacion("Triv that man", 3, R.drawable.trivales, "Trivales improvisados y bien currados"));
        publicaciones.add(new Publicacion("nice DIY pants", 4, R.drawable.pantalones, "Unos pantalones bien tuneados"));
        publicaciones.add(new Publicacion("Bugs", 5, R.drawable.bug, "bugs todo extraños pero que molan un monton"));
        publicaciones.add(new Publicacion("Skely Pony!", 6, R.drawable.red_pony, "El pony de deftones con un pedazo de estilo y hecho con forma de esqueleto"));
        publicaciones.add(new Publicacion("Graffiti Mike", 7, R.drawable.pintada_lp, "Mike Shinoda pintando con graffiti en una posiion muy chula"));
        publicaciones.add(new Publicacion("Entes misteriosos", 8, R.drawable.entes, "Entes extraños que me encontre por internet y que molan un monton"));
        publicaciones.add(new Publicacion("Paisaje acantilado", 9, R.drawable.acantilado, "Un fondo oscuro del mar con la luna de fondo"));
        publicaciones.add(new Publicacion("deftones skull", 10, R.drawable.deftones_skull, "Fondo con la calavera de deftones con unos colores claros"));
        publicaciones.add(new Publicacion("Lain edit!", 11, R.drawable.lain, "fotaza de Lain, con un buen edit"));

        return publicaciones;
    }
}
