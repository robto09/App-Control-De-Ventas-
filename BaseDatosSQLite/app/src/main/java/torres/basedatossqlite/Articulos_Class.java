package torres.basedatossqlite;

/**
 * Created by Robert on 06/08/2016.
 */
public class Articulos_Class {
    public String nombre;
    public String Precio;

    public Articulos_Class() {}

    public Articulos_Class(String elnombre, String elPrecio) {
        this.nombre = elnombre;
        this.Precio = elPrecio;
    }

    public void setNombre(String elnombre) {
        this.nombre = elnombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setPrecio(String elPrecio) {
        this.Precio = elPrecio;
    }

    public String getPrecio() {
        return this.Precio;
    }
}
