package com.example.wikz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Usuario {

    public Usuario(String nombre, String pass, String correo){

        setNommbre(nombre);
        setPass(pass);
        setCorreo(correo);
        setFechaCreacion(new Date());
    }

    public Usuario(){
        this("", "", "");
    }
    private String nombre;
    public void setNommbre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return nombre;
    }

    private  String pass;
    public void setPass(String pass){
        this.pass = pass;
    }
    public String getPass(){
        return  pass;
    }

    private  String correo;
    public void setCorreo(String correo){
        this.correo = correo;
    }
    public String getCorreo(){
        return  correo;
    }

    private Date fechaCreacion;

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }
}
