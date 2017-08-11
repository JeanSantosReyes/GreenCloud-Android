package Model;

/**
 * Created by MAND on 09/08/2017.
 */
public class Invernadero {
    private int id_invernadero;
    private String nombre;
    private int id_user;
    private String img;
    public Invernadero(int id_invernadero,String nombre,String img,int id_user){
        this.id_invernadero = id_invernadero;
        this.nombre = nombre;
        this.id_user = id_user;
        this.img = img;
    }

    public int getId_invernadero() {
        return id_invernadero;
    }

    public void setId_invernadero(int id_invernadero) {
        this.id_invernadero = id_invernadero;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return this.nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
