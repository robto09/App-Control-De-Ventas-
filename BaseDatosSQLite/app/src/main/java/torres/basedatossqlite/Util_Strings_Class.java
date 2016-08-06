package torres.basedatossqlite;

/**
 * Created by Robert on 06/08/2016.
 */
public class Util_Strings_Class {

    public Util_Strings_Class(){}//Constructor de la clase

    public String[] CortaTextos(String elTexto, String elCaracter){
        String[] partes = elTexto.split(elCaracter);
        return partes;
    }//Fin CortaTextos =======================
}