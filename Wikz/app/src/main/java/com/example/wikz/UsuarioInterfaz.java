package com.example.wikz;

public class UsuarioInterfaz extends Usuario {

    public UsuarioInterfaz(String nombre, String pass, String correo, String descripcion, int fotoPerfil){

        super(nombre, pass, correo);

        setDescripcion(descripcion);
        setFotoPerfil(fotoPerfil);
    }

    public  UsuarioInterfaz(){

        super("", "", "");
        setDescripcion("");
        setFotoPerfil(0);
    }
    private String descripcion;
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    private int fotoPerfil;
    public void setFotoPerfil(int foto){
        this.fotoPerfil = foto;
    }
    public int getFotoPerfil(){
        return  fotoPerfil;
    }
}
