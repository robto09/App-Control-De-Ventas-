package torres.basedatossqlite;

/**
 * Created by Robert on 06/08/2016.
 */
public class Clientes_Class {
    public String nombre;
    public String telefono;

    public Clientes_Class() { } //Constructor #1

    public Clientes_Class(String elnombre, String eltelefono) {
        this.nombre = elnombre;
        this.telefono = eltelefono;
    }

    public void setNombre(String elnombre) {
        this.nombre = elnombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setTelefono(String eltelefono) {
        this.telefono = eltelefono;
    }

    public String getTelefono() {
        return this.telefono;
    }

}