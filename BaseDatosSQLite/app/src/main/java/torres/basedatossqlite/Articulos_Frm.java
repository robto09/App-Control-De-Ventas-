package torres.basedatossqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Articulos_Frm extends Activity implements View.OnClickListener{

    EditText articuloTxt;
    EditText precioTxt;
    EditText codigoTxt;
    Button VuelveBtn;
    Button SelecArticBtn;
    ListView ListaProductos;

    SQLite_Class controlDB = new SQLite_Class(this);

    Util_Strings_Class UtileStrings = new Util_Strings_Class();
    Util_UI_Class UtilesUI = new Util_UI_Class();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos__frm);

        incializaPantalla();
        ListaProductos.setAdapter(UtilesUI.CargaArrayAdapter(this, controlDB.ConsultArticulos()));
        ListViewClick();
    }

    private void ListViewClick(){
        ListaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int laPosicion = position + 1;
                String elementoSeleccionado = (String) ListaProductos.getItemAtPosition(position);
                String[] TextoPartido = UtileStrings.CortaTextos(elementoSeleccionado, ",");
                codigoTxt.setText(String.valueOf(laPosicion));
                articuloTxt.setText(TextoPartido[0]);
                precioTxt.setText(TextoPartido[1]);
            }
        });//End setOnItemClickListener
    }//Fin ListViewClick

    public void incializaPantalla() {
        codigoTxt = (EditText) findViewById(R.id.CodArt_Txt);
        articuloTxt = (EditText) findViewById(R.id.NomProd_Txt);
        precioTxt = (EditText) findViewById(R.id.Precio_Txt);
        ListaProductos = (ListView) findViewById(R.id.ProductosView);
        VuelveBtn = (Button) findViewById(R.id.BackProd_Btn);
        VuelveBtn.setOnClickListener(this);

    }//Fin incializaPantalla

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.BackProd_Btn:
                startActivity(new Intent(Articulos_Frm.this, Principal.class));
                break;
        }//End switch
    }//Fin onClick

}
