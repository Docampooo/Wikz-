package clases;

import java.util.Date;

public class Usuario {

    public Usuario(String nombre, String correo, String pass, String biografia, String fotoPerfil, Date fechaCreacion) {

        setNombre(nombre);
        setPass(pass);
        setEmail(correo);

        setBiografia(biografia);
        setFotoPerfilBase64(fotoPerfil);

        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : new Date();

    }

    public Usuario() {
        this("", "", "", "", null, new Date());
    }

    private String nombre;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    private int id;

    public void setId(int id) {
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

    private String fotoPerfilBase64;

    public String getFotoPerfilBase64() {
        return fotoPerfilBase64;
    }

    public void setFotoPerfilBase64(String fotoPerfil) {
        this.fotoPerfilBase64 = fotoPerfil;
    }

    public String toString() {
        return "Usuario{id=" + id + ", nombre=" + nombre + ", email=" + email + "}";
    }

}