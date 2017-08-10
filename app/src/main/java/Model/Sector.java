package Model;

/**
 * Created by MAND on 09/08/2017.
 */
public class Sector {
    private int idsector,id_invernadero;
    private String nombre,posicion_x,posicion_y;

    public Sector(int idsector,String nombre,String posicion_x,String posicion_y,int id_invernadero){
        this.idsector = idsector;
        this.id_invernadero = id_invernadero;
        this.nombre = nombre;
        this.posicion_x = posicion_x;
        this.posicion_y = posicion_y;
    }


    public int getId_invernadero() {
        return id_invernadero;
    }

    public void setId_invernadero(int id_invernadero) {
        this.id_invernadero = id_invernadero;
    }

    public int getIdsector() {
        return idsector;
    }

    public void setIdsector(int idsector) {
        this.idsector = idsector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion_x() {
        return posicion_x;
    }

    public void setPosicion_x(String posicion_x) {
        this.posicion_x = posicion_x;
    }

    public String getPosicion_y() {
        return posicion_y;
    }

    public void setPosicion_y(String posicion_y) {
        this.posicion_y = posicion_y;
    }
}
