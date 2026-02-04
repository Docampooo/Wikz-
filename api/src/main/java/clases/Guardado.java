package clases;

public class Guardado {

    public Guardado(int id_coleccion, int id_publicacion){

        setIdColeccion(id_coleccion);
        setIdPublicacion(id_publicacion);
    }

    public Guardado(){

        setIdColeccion(0);
        setIdPublicacion(0);
    }
    
    private int id_coleccion;
    private int id_publicacion;

    public int getIdColeccion() {
        return id_coleccion;
    }

    public void setIdColeccion(int id_coleccion) {
        this.id_coleccion = id_coleccion;
    }

    public int getIdPublicacion() {
        return id_publicacion;
    }

    public void setIdPublicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
}
