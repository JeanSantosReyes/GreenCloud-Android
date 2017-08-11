package Model;

/**
 * Created by MAND on 10/08/2017.
 */
public class variedad {
    public String name;
    public String foto;
    public variedad(String name,String foto){
        this.name = name;
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
