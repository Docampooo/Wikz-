package clases;

import java.util.Date;

public class Usuario {

    public Usuario(String nombre, String correo, String pass, String biografia, byte[] fotoPerfil) {

        setNommbre(nombre);
        setPass(pass);
        setEmail(correo);

        setBiografia(biografia);
        setFotoPerfil(fotoPerfil);

        setFechaCreacion(new Date());
    }

    public Usuario() {
        this("", "", "", "", null);
    }

    private String nombre;

    public void setNommbre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    private byte[] fotoPerfil;

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    private int id;

    public void setId(int id){
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    private String pass;

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    private Date fechaCreacion;

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    private String biografia;

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getBiografia() {
        return biografia;
    }
}