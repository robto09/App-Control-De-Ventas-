package torres.basedatossqlite;

import android.content.Context;

/**
 * Created by Robert on 07/08/2016.
 */
public class CargaDatos_Class {
    SQLite_Class controlDB;

    public CargaDatos_Class(Context elContexto){
        controlDB = new SQLite_Class(elContexto);
    }

    public void GuardaArticulo(String elnombre, String elprecio){
        Articulos_Class Articulo = new Articulos_Class();
        Articulo.Precio= elprecio;
        Articulo.nombre=elnombre;
        controlDB.InsertArticulo(Articulo);
    }//Fin CargaClientesTest  =======================

    public void GuardaCliente(String elnombre, String eltelefono){
        Clientes_Class Persona = new Clientes_Class();
        Persona.telefono= eltelefono;
        Persona.nombre=elnombre;
        controlDB.InsertaCliente(Persona);
    }//Fin CargaClientesTest  =======================



    private void CargaDatosArticulos(){
        controlDB.EliminaTablas("Articulos");

        //Aqui va el for que recorra el Web Service o el XML
 /*       GuardaArticulo("Salchichon", "100");
        GuardaArticulo("Mortadela", "300");
        GuardaArticulo("Salchichas", "700");*/
    }//Fin CargaDatosPrueba  =======================

    private void CargaDatosProductos(){
        controlDB.EliminaTablas("Clientes");
        //Utiles.MensajeToast(getApplicationContext(), String.valueOf(controlDB.CuentaClientes()));

        //Aqui va el for que recorra el Web Service o el XML
     /*   GuardaCliente("Rosa", "1111");
        GuardaCliente("Michael", "2222");
        GuardaCliente("Ruperto", "3333");*/
    }//Fin CargaDatosPrueba  =======================

    public void SincronizaTodo(){
        CargaDatosProductos();
        CargaDatosArticulos();
    }//SincronizaTodo


}//Fin CargaDatos_Class

