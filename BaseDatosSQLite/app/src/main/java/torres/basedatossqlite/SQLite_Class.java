package torres.basedatossqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Robert on 06/08/2016.
 */
public class SQLite_Class extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PuntoVentaDB.db";

    private static final String COL_ID = "id";
    private static final String COL_Nombre = "nombre";
    private static final String COL_Telefono = "telefono";

    private static final String COL_ID_Prod = "id";
    private static final String COL_Nom_Prod = "nombre";
    private static final String COL_Precio = "precio";


    private static final String CREA_TABLA_PERSONA =
            "CREATE TABLE IF NOT EXISTS Clientes (" + COL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    COL_Nombre + " TEXT, " + COL_Telefono + " TEXT )";

    private static final String CREA_TABLA_ARTICULOS =
            "CREATE TABLE IF NOT EXISTS Articulos (" + COL_ID_Prod  + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    COL_Nom_Prod + " TEXT, " + COL_Precio + " TEXT )";



    public SQLite_Class(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }//Fin Constructor  =======================

    public SQLite_Class(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }//Fin Constructor  =======================

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREA_TABLA_PERSONA);
        db.execSQL(CREA_TABLA_ARTICULOS);
        //db.execSQL(CREA_TABLA_INVOICE);
    }//Fin onCreate  =======================

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borra las tablas si existen y todos los datos se borran.
        db.execSQL("DROP TABLE IF EXISTS Clientes");
        db.execSQL("DROP TABLE IF EXISTS Articulos");
        //db.execSQL("DROP TABLE IF EXIST Invoice");
        onCreate(db);// Crea la tabla otra vez
    }//Fin onUpgrade =======================

    public int InsertArticulo(Articulos_Class Articulo) {
        ContentValues values = new ContentValues();
        values.put(COL_Nom_Prod, Articulo.nombre);
        values.put(COL_Precio, Articulo.Precio);

        SQLiteDatabase db = this.getWritableDatabase();
        long Articulo_Id = db.insert("Articulos", null, values);
        db.close();// Cierra la conexión con la BD
        return (int) Articulo_Id;
    }// Fin InsertaCliente =======================

    public int InsertaCliente(Clientes_Class persona) {
        ContentValues values = new ContentValues();
        values.put(COL_Nombre, persona.nombre);
        values.put(COL_Telefono, persona.telefono);

        SQLiteDatabase db = this.getWritableDatabase();
        long cliente_Id = db.insert("Clientes", null, values);
        db.close();// Cierra la conexión con la BD
        return (int) cliente_Id;
    }// Fin InsertaCliente =======================




    public void BorraCliente(int cliente_Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Clientes", COL_ID + "= ?", new String[] { String.valueOf(cliente_Id) });
        db.close();// Cierra la conexión con la BD
    }// Fin BorraPersona =======================

    public void BorraTodoCliente(){
        for (int i =1; i < CuentaClientes(); i++ ) {
            BorraCliente(i);
        }//Fin For
    }//Fin BorraTodoCliente =======================

    public void EliminaTablas(String laTabla){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + laTabla);
        onCreate(db);// Crea la tabla otra vez
    }//Fin BorraTodoCliente =======================

    public int CuentaClientes(){
        int CantElementos = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT Count( " + COL_ID + ") as CantClientes FROM Clientes";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CantElementos = cursor.getInt(cursor.getColumnIndex("CantClientes"));
            } while (cursor.moveToNext());
        }//if
        cursor.close();
        db.close();
        return CantElementos;
    }//Fin CuentaClientes =======================

    public ArrayList<String> ConsultaClientes() {
        String Nombre = "";
        String Telefono = "";
        ArrayList<String> ListaPersonas = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Clientes", null);

        while(cursor.moveToNext())
        {
            Telefono = cursor.getString(cursor.getColumnIndex(COL_Telefono));
            Nombre = cursor.getString(cursor.getColumnIndex(COL_Nombre));
            ListaPersonas.add(Telefono + ", " + Nombre);
            Nombre = "";
            Telefono = "";
        }//Fin while

        cursor.close();
        db.close();
        return ListaPersonas;
    } //Fin ConsultaClientes =======================



    public ArrayList <String> ConsultArticulos() {
        String Nombre = "";
        String Precio = "";
        ArrayList<String> ListaArticulos = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Articulos", null);

        while(cursor.moveToNext())
        {
            Precio = cursor.getString(cursor.getColumnIndex(COL_Precio));
            Nombre = cursor.getString(cursor.getColumnIndex(COL_Nom_Prod));
            ListaArticulos.add(Nombre +", "+Precio);

        }//Fin while

        cursor.close();
        db.close();
        return ListaArticulos;
    } //Fin ConsultaClientes =======================



    public Clientes_Class BuscaPersona_x_ID(int Id){
        int iCount =0;
        Clientes_Class persona = new Clientes_Class();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " + COL_ID + "," +
                COL_Nombre + "," + COL_Telefono +
                " FROM Clientes WHERE " + COL_ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                persona.nombre =cursor.getString(cursor.getColumnIndex(COL_Nombre));
                persona.telefono =cursor.getString(cursor.getColumnIndex(COL_Telefono));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return persona;
    }//

}// Fin LibSQLIte